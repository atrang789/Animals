package com.beepbop.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.beepbop.animals.model.Animal

//Expose livedata variables to use
class ListViewModel (application: Application): AndroidViewModel(application) {
    //lazy only create it when needed
    //liveData observable that provides data
    val animals by lazy { MutableLiveData<List<Animal>> ()}
    val loadError by lazy {MutableLiveData<Boolean> ()}
    val loading by lazy {MutableLiveData<Boolean> ()}

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal("alligator")
        val a2 = Animal("penguin")
        val a3 = Animal("bee")
        val a4 = Animal("whale")
        val a5 = Animal("shark")
        val a6 = Animal("eel")

        val animalList : ArrayList<Animal> = arrayListOf(a1, a2, a3, a4, a5, a6)
        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}