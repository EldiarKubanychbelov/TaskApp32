package kg.geektech.taskapp31.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.taskapp31.App;
import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.interfaces.OnItemClickListener;
import kg.geektech.taskapp31.models.Task;
import kg.geektech.taskapp31.ui.home.TaskAdapter;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_list_farebace);
        initList();
        getDataFromFirestore();
    }

    private void getDataFromFirestore() {
        FirebaseFirestore
                .getInstance()
                .collection("tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                List<Task> list = new ArrayList<>();
                for (DocumentSnapshot snapshot : snapshots) {
                    String docId = snapshot.getId();
                    String title = snapshot.getString("title");
                    Task task = new Task(title);
                    task.setDocId(docId);
                    list.add(task);
                }
//                List <Task> list = snapshots.toObjects(Task.class);
                adapter.addItems(list);
            }
        });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickStart(int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete ?");
                builder.setPositiveButton("Oa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteFromFirestore(position);
                    }
                });
                builder.setNegativeButton("JOk", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void DeleteFromFirestore(int position){
        Task task = adapter.getItem(position);
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(task.getDocId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(requireContext(), "Чау еврибади", Toast.LENGTH_SHORT).show();
                adapter.remove(position);
            }
        });
    }
}