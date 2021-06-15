package kg.geektech.taskapp31.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import kg.geektech.taskapp31.App;
import kg.geektech.taskapp31.Prefs;
import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.models.Task;


public class ProfileFragment extends Fragment {

    private EditText edUserName,edEmail;
    private ImageView imgProfile;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edUserName = view.findViewById(R.id.ed_name);
        edEmail = view.findViewById(R.id.ed_email);
        imgProfile = view.findViewById(R.id.img_profile);

        String text = edUserName.getText().toString();

        Task task = new Task(text);
        App.getAppDatabase().taskDao().insert(task);


        Prefs prefs = new Prefs(getContext());
        edUserName.setText(prefs.getString("autoSave"));
        edEmail.setText(prefs.getString("autoSave1"));

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.putString("autoSave1je", s.toString());
            }
        });


        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.putString("autoSave", s.toString());
            }
        });


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                launchSomeActivity.launch(intent);
            }
        });



    }


    public ActivityResultLauncher launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        imgProfile.setImageURI(uri);

                        imgProfile.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                String strUri = uri.toString();
                                Bundle bundle = new Bundle();
                                bundle.putString("uri",strUri);
                                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                                navController.navigate(R.id.fullScreenFragment,bundle);
                                return true;
                            }
                        });

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference reference = storage.getReference();

                        StorageReference ref = reference.child("image/"+uri.getPathSegments());
                        UploadTask uploadTask = (UploadTask) ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(requireContext(), "uploaded image", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
}




