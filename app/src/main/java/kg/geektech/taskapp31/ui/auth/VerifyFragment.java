package kg.geektech.taskapp31.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.databinding.FragmentVerifyBinding;

public class VerifyFragment extends Fragment {

    FragmentVerifyBinding binding;
    private String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        s = getArguments().getString("s");

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edVerify.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "пусто", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = binding.edVerify.getText().toString();
                if (s != null) {

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(s,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(requireContext(), "успешно", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                                navController.navigate(R.id.navigation_home);
                            }else {
                                Toast.makeText(getActivity(), "не правильно введен код!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


        new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                binding.txtTimer.setText(text);
            }

            @Override
            public void onFinish() {
                Navigation.findNavController(view).popBackStack();
            }
        }.start();
    }
}