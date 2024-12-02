package com.example.studentmanager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddStudentFragment : Fragment() {

    private lateinit var studentNameEditText: EditText
    private lateinit var studentIdEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_student_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentNameEditText = view.findViewById(R.id.edit_hoten)
        studentIdEditText = view.findViewById(R.id.edit_mssv)

        view.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val studentName = studentNameEditText.text.toString()
            val studentId = studentIdEditText.text.toString()

            // Tạo kết quả và điều hướng về Fragment trước đó
            val result = Bundle().apply {
                putString("StudentName", studentName)
                putString("StudentId", studentId)
                putBoolean("isNew", true)
            }
            parentFragmentManager.setFragmentResult("requestKey", result)
            findNavController().navigateUp() // Quay lại Fragment trước
        }

        view.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            findNavController().navigateUp() // Quay lại Fragment trước
        }
    }
}