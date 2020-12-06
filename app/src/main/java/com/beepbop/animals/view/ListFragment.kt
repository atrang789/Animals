package com.beepbop.animals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.beepbop.animals.R
import com.beepbop.animals.adapters.AnimalListAdapter
import com.beepbop.animals.model.Animal
import com.beepbop.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>> {list ->
        list?.let {
            animal_list.visibility = View.VISIBLE
            listAdapter.updateAnimalList(list)
        }
    }

    private val loadingLiveObserver = Observer<Boolean> {isLoading ->
        loading_bar.visibility = if(isLoading) View.VISIBLE else View.GONE
        if(isLoading) {
            animal_list.visibility = View.GONE
            list_error.visibility = View.GONE
        }
    }

    private val errorLiveObserver = Observer<Boolean> {isError ->
        list_error.visibility = if(isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //handles lifecycle events
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveObserver)
        viewModel.loadError.observe(this, errorLiveObserver)
        viewModel.refresh()

        animal_list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        swipe_refresh.setOnRefreshListener {
            list_error.visibility = View.GONE
            animal_list.visibility = View.GONE
            loading_bar.visibility = View.VISIBLE
            viewModel.refresh()
            swipe_refresh.isRefreshing = false
        }

        button_list.setOnClickListener {
            val action = ListFragmentDirections.actionDetail()
            Navigation.findNavController(it).navigate(action)
        }
    }
}