package com.example.latihan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebase_fragment extends Fragment {
    private EditText namamtk;
    private EditText sks;
    private EditText dosen;
    private Button buttonSimpan;
    private Button buttonHapus;
    private FirebaseFirestore firebaseFirestoreDb;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firebase, container, false);
        namamtk = view.findViewById(R.id.namaMtk);
        sks = view.findViewById(R.id.sks);
        dosen = view.findViewById(R.id.dosen);
        buttonSimpan = view.findViewById(R.id.simpanButton);
        buttonHapus = view.findViewById(R.id.hapusButton);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!namamtk.getText().toString().isEmpty() && !sks.getText().toString().isEmpty()&& !dosen.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(requireActivity(), "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void tambahMahasiswa() {
        Matakuliah mhs = new Matakuliah(namamtk.getText().toString(),
                Integer.parseInt(sks.getText().toString()),
                dosen.getText().toString());

        firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
    private void getDataMahasiswa() {
        DocumentReference docRef = firebaseFirestoreDb.collection("DaftarMhs").document("mhs1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Matakuliah mhs = document.toObject(Matakuliah.class);
                        namamtk.setText(mhs.getNamamtk());
                        sks.setText(mhs.getSks());
                        dosen.setText(mhs.getDosen());
                    } else {
                        Toast.makeText(requireActivity(), "Document tidak ditemukan",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Document error : " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void deleteDataMahasiswa() {
        firebaseFirestoreDb.collection("DaftarMhs").document("mhs1")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        namamtk.setText("");
                        sks.setText("");
                        dosen.setText("");
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataMahasiswa();
            }
        });

    }

}
