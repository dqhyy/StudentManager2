package com.example.studentmanager2

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentListFragment : Fragment() {

    private val students = mutableListOf(
        StudentModel("Student 1", "SV001"),
        StudentModel("Student 2", "SV002"),
        StudentModel("Student 3", "SV003"),
        StudentModel("Student 4", "SV004"),
        StudentModel("Student 5", "SV005"),
        StudentModel("Student 6", "SV006"),
        StudentModel("Student 7", "SV007"),
        StudentModel("Student 8", "SV008"),
        StudentModel("Student 9", "SV009"),

        )

    private lateinit var studentAdapter: StudentAdapter
    private var selectedStudentPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_student_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        studentAdapter = StudentAdapter(students) { position ->
            selectedStudentPosition = position
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_students)
            val itemView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
            itemView?.let {
                // Mở Context Menu cho itemView
                it.showContextMenu()
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_students).apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            registerForContextMenu(this) // Đăng ký cho Context Menu
        }

        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val studentName = bundle.getString("StudentName", "")
            val studentId = bundle.getString("StudentId", "")
            val isNew = bundle.getBoolean("isNew", false)
            if (isNew) {
                students.add(StudentModel(studentName, studentId))
                studentAdapter.notifyDataSetChanged()
            }
            else {
                students[selectedStudentPosition].name = studentName
                students[selectedStudentPosition].studentId = studentId
                studentAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_new -> {
                findNavController().navigate(R.id.addStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_edit -> {
                val student = students[selectedStudentPosition]
                val args = Bundle().apply {
                    putString("StudentName", student.name)
                    putString("StudentId", student.studentId)
                }
                findNavController().navigate(R.id.action_studentListFragment_to_editStudentFragment, args)
                true
            }
            R.id.context_delete -> {
                students.removeAt(selectedStudentPosition)
                studentAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}