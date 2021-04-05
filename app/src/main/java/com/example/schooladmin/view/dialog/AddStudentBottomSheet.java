package com.example.schooladmin.view.dialog;import android.app.ProgressDialog;import android.content.BroadcastReceiver;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.content.IntentFilter;import android.os.AsyncTask;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.EditText;import android.widget.Spinner;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.fragment.app.DialogFragment;import com.example.schooladmin.R;import com.example.schooladmin.connectdb.ConnectDB;import com.example.schooladmin.model.ClassSchool;import com.example.schooladmin.model.Student;import com.example.schooladmin.query.StudentQuery;import com.example.schooladmin.view.adapter.ClassSpinnerAdapter;import com.example.schooladmin.view.fargment.StudentFragment;import com.google.android.material.bottomsheet.BottomSheetDialogFragment;import java.sql.Connection;import java.sql.ResultSet;import java.sql.ResultSetMetaData;import java.sql.Statement;import java.util.ArrayList;import java.util.List;public class AddStudentBottomSheet extends BottomSheetDialogFragment {    private Spinner spinnerBootomSheetAddStudentClassName;    private List<ClassSchool> classes;    private ClassSpinnerAdapter classSpinnerAdapter;    private EditText edtBottomSheetAddStudentName;    private EditText edtBottomSheetAddStudentPhone;    private EditText edtBottomSheetAddStudentPassword;    private Button btnBottomSheetAddStudenetSave;    private ProgressDialog progressDialog;    private StudentQuery studentQuery;    private ConnectDB connectDB;    @Override    public void onCreate(@Nullable Bundle savedInstanceState) {        super.onCreate( savedInstanceState );        setStyle( DialogFragment.STYLE_NORMAL, R.style.DialogStyle );    }    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater,                             @Nullable ViewGroup container,                             @Nullable Bundle savedInstanceState) {        View view = inflater.inflate( R.layout.bottom_sheet_add_student, container );        connectDB = new ConnectDB();        studentQuery = new StudentQuery( getContext() );        progressDialog = new ProgressDialog( getContext() );        edtBottomSheetAddStudentName = view.findViewById( R.id.edtBottomSheetAddStudentName );        edtBottomSheetAddStudentPhone = view.findViewById( R.id.edtBottomSheetAddStudentPhone );        edtBottomSheetAddStudentPassword = view.findViewById( R.id.edtBottomSheetAddStudentPassword );        btnBottomSheetAddStudenetSave = view.findViewById( R.id.btnBottomSheetAddStudenetSave );        spinnerBootomSheetAddStudentClassName = view.findViewById( R.id.spinnerBootomSheetAddStudentClassName );        classes = new ArrayList<>();        studentQuery.callTaskGetClass();        classSpinnerAdapter = new ClassSpinnerAdapter( classes, getActivity() );        spinnerBootomSheetAddStudentClassName.setAdapter( classSpinnerAdapter );        IntentFilter intentFilterClass = new IntentFilter( "classList" );        requireActivity().registerReceiver( boBroadcastReceiverAllClass, intentFilterClass );        btnBottomSheetAddStudenetSave.setOnClickListener( new View.OnClickListener() {            @Override            public void onClick(View view) {                String name = edtBottomSheetAddStudentName.getText().toString().trim();                String password = edtBottomSheetAddStudentPassword.getText().toString().trim();                String phone = edtBottomSheetAddStudentPhone.getText().toString().trim();                ClassSchool classSchool = classes.get( (int) spinnerBootomSheetAddStudentClassName.getSelectedItemId() );                int classID = classSchool.getId();                if (name.equals( "" ) || password.equals( "" ) || phone.equals( "" )) {                    Toast.makeText( getActivity(), "Please fill all record and agine", Toast.LENGTH_SHORT ).show();                    return;                }                progressDialog.setMessage( "Inserting..." );                progressDialog.show();                IntentFilter intentFilterStateInsert = new IntentFilter( "stateInsertStudent" );                requireActivity().registerReceiver( broadcastReceiverStateInsertStudent, intentFilterStateInsert );                studentQuery.callTaskInsertStudent( new Student( 0, name, phone, password, classID ) );            }        } );        return view;    }    //for get All Class    BroadcastReceiver boBroadcastReceiverAllClass = new BroadcastReceiver() {        @Override        public void onReceive(Context context, Intent intent) {            classes = studentQuery.getClasses();            classSpinnerAdapter.setData( classes );        }    };    //for Insert Student    BroadcastReceiver broadcastReceiverStateInsertStudent = new BroadcastReceiver() {        @Override        public void onReceive(Context context, Intent intent) {            if (studentQuery.getStateInsertStudent()) {                progressDialog.hide();                dismiss();            } else {                Toast.makeText( getActivity(), "Faild", Toast.LENGTH_SHORT ).show();            }        }    };}