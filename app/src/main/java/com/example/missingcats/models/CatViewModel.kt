package com.example.missingcats.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.missingcats.repository.CatRepository

class CatViewModel : ViewModel() {
    private val repository = CatRepository()
    val catLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData
    init {
        reload()
    }

    fun reload(){
        repository.getPosts()
    }

    operator fun get(index: Int): Cat? {
        return catLiveData.value?.get(index)
    }

    fun add(cat: Cat){
        repository.add(cat)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(cat: Cat) {
        repository.update(cat)
    }
}