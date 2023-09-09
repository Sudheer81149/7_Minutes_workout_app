package com.devdroid.a7minutesworkout

class ExerciseModel(
    private var id:Int,
     private var name:String,
    private var image:Int,
    private var  isCompletated:Boolean,
    private var isSelected:Boolean
){
    fun getId():Int{
        return id
    }
    fun setId(id: Int){
        this.id=id
    }
    fun getName():String{
        return name
    }
    fun setName(name: String){
        this.name=name
    }
    fun getImage():Int{
        return image
    }
    fun setImage(image: Int){
        this.image=image
    }
    fun getIsCompeted():Boolean{
        return isCompletated
    }
    fun setIsComplected(iscomplected: Boolean){
        this.isCompletated=iscomplected
    }
    fun getIsSelected():Boolean{
        return isSelected
    }
    fun setIsSelected(isselected: Boolean){
        this.isSelected=isselected
    }

}