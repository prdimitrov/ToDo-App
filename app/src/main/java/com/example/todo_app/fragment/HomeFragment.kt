package com.example.todo_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todo_app.MainActivity
import com.example.todo_app.R
import com.example.todo_app.adapter.NoteAdapter
import com.example.todo_app.databinding.FragmentHomeBinding
import com.example.todo_app.model.Note
import com.example.todo_app.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), OnQueryTextListener {

    private var binding: FragmentHomeBinding? = null
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable the options menu for this fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()

        binding?.homeFragmentButtonAddNote?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()

        binding?.let { binding ->
            binding.homeFragmentRecyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
                adapter = noteAdapter
            }

            activity?.let {
                notesViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
                    noteAdapter.submitList(notes)
                    updateUI(notes)
                }
            }
        }
    }

    private fun updateUI(notes: List<Note>?) {
        if (!notes.isNullOrEmpty()) {
            binding?.homeCardView?.visibility = View.GONE
            binding?.homeFragmentRecyclerView?.visibility = View.VISIBLE
        } else {
            binding?.homeCardView?.visibility = View.VISIBLE
            binding?.homeFragmentRecyclerView?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_home, menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchNote(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { searchNote(it) }
        return true
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { list ->
            noteAdapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
