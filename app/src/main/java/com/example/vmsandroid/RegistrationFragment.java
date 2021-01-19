package com.example.vmsandroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.zxing.WriterException;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends Fragment {

    EditText mResultEt;
    EditText ic;
    EditText address;
    EditText name;
    ImageView mPreviewIv;
    ImageView qrImage;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    private int cont = 0;
    int index = 0;
    int indexofList = 0;
    int positionofIC = 0;
    boolean ICchecked = false;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

    private String QRText = "";
    private String oriname = "";
    private String oriic = "";
    private String oriaddress = "";

    Button uploadic;
    Button generateQR;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Registration");
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_registration, container, false);

        HttpLoggingInterceptor loggingInterceptor =  new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        mPreviewIv = v.findViewById(R.id.imageIc);
        ic = v.findViewById(R.id.visitorIC);
        address = v.findViewById(R.id.visitorAddress);
        name = v.findViewById(R.id.visitorName);
        generateQR = v.findViewById(R.id.generateQR);
        qrImage = v.findViewById(R.id.qrImage);
        qrImage.setImageResource(android.R.color.transparent);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreviewIv.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Image needs to be uploaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    createQRentry();
                    }
                }
        });

        Button button = (Button) v.findViewById(R.id.uploadIc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }

            private void showImageImportDialog() {
                //items to display in dialog
                String[] items = {" Camera", " Gallery"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                //set title
                dialog.setTitle("Select Image");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //camera option clicked
                            if (!checkCameraPermission()) {
                                //camera permission not allowed, request it
                                requestCameraPermission();
                            } else {
                                //permission allowed, take picture
                                pickCamera();
                            }
                        }
                        if (which == 1) {
                            //gallery option clicked
                            if (!checkStoragePermission()) {
                                requestStoragePermission();
                            } else {
                                //permission allowed, select picture
                                pickGallery();
                            }
                        }
                    }
                });
                dialog.create().show();

            }
        });

        return v;
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/#");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        //intent to take image from camera, it will also be save to storage to get high quality image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic"); //title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");// description
        image_uri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //handle permission result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery now crop it
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
                        .start(getContext(),this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera now crop it
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
                        .start(getContext(), this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri(); //get image Uri
                //set image to image view
                mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

                detector.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        displayTextFromImage(result);
                        positionofIC = 0;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.d("Error: ", e.getMessage());
                    }
                });
            }
        }
    }

    private void displayTextFromImage(FirebaseVisionText result) {
//        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
//        if (blockList.size() == 0){
//            Toast.makeText(this, "No Text Found in image.", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            StringBuilder sb = new StringBuilder();
//            for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks())
//            {
//                String text = block.getText();
//                sb.append(text);
//                sb.append("\n\n");
//            }
//            mResultEt.setText(sb.toString());
//        }
        List<FirebaseVisionText.TextBlock> blockList = result.getTextBlocks();
        if (blockList.size() == 0){
            Toast.makeText(getActivity(), "No Text Found in image.", Toast.LENGTH_SHORT).show();
        }
        else{
            StringBuilder sb = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            for (FirebaseVisionText.TextBlock block: result.getTextBlocks()){
                String results = block.getText();
                sb3.append(results);
                for (FirebaseVisionText.Line line : block.getLines()){
                    String lineText = line.getText();
                    for (FirebaseVisionText.Element element : line.getElements()) {
                        String elementText = element.getText();
                        if(elementText.contains("9")) {
                            ICchecked = true;
                            positionofIC = index;
                            break;
                        }
                    }
                }
                index ++;
            }
        }
        StringBuilder icDetails = new StringBuilder();
        StringBuilder nameDetails = new StringBuilder();
        StringBuilder addressDetails = new StringBuilder();
        for (FirebaseVisionText.TextBlock block2: result.getTextBlocks()){
            indexofList ++;
            if (indexofList ==positionofIC + 1){
                String details = block2.getText();
                icDetails.append(details);
            }
            if (indexofList ==positionofIC + 2){
                String details = block2.getText();
                nameDetails.append(details);
            }
            if (indexofList ==positionofIC + 3){
                String details = block2.getText();
                addressDetails.append(details);
            }

        }

        oriic = icDetails + "";
        oriname = nameDetails + "";
        oriaddress = addressDetails + "";
        ic.setText(icDetails);
        name.setText(nameDetails);
        address.setText(addressDetails);
        QRText = ic.getText().toString() + ";" + name.getText().toString() + ";" + address.getText().toString();
    }

    private void createQRentry(){
        HashMap<String, String> detailstore = new HashMap<>();

        detailstore.put("oriname", oriname);
        detailstore.put("oriic", oriic);
        detailstore.put("oriaddress", oriaddress);
        detailstore.put("name", name.getText().toString());
        detailstore.put("ic", ic.getText().toString());
        detailstore.put("address", address.getText().toString());


        Call<qrentry> call = retrofitInterface.qrdetailEntry(detailstore);

        call.enqueue(new Callback<qrentry>() {
            @Override
            public void onResponse(Call<qrentry> call, Response<qrentry> response) {
                    if(response.code() == 400){
                        Toast.makeText(getActivity(), "Visitor has not checked out", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String data = QRText;
                        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 1000);
                        Bitmap qrBits = qrgEncoder.getBitmap();
                        qrImage.setImageBitmap(qrBits);
                        Toast.makeText(getActivity(), "Please share your QR Code to the visitor", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<qrentry> call, Throwable t) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}