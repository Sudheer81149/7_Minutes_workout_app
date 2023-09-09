package com.devdroid.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.devdroid.a7minutesworkout.databinding.ActivityExerciesBinding
import com.devdroid.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.Locale

class ExerciesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciesBinding? =null
    private var restTimmer:CountDownTimer? =null
    private var restprogress=0
    private var ExerciesRestTimmer:CountDownTimer? =null
    private var ExerciesRestprogress=0
    private var ExerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var restTimeDuration:Long=1
    private var exerciesTimeDuration:Long=1
    private var tts:TextToSpeech? =null
    private var player:MediaPlayer?=null
    private var exerciesviewAdapter:ExerciesStatusAdapter?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciesBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.exerciestoolbar)
        if(supportActionBar !=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.exerciestoolbar?.setNavigationOnClickListener {
            coustomDiologForBackButton()
        }
        ExerciseList =Constants.defaultExerciesList()
        tts = TextToSpeech(this,this)

        setRestView()
        SetUpExerciesRecycleView()
    }

    override fun onBackPressed() {
        coustomDiologForBackButton()
    }

    private fun coustomDiologForBackButton(){
        val coustomDailog= Dialog(this)
        val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        coustomDailog.setContentView(dialogBinding.root)
        coustomDailog.setCanceledOnTouchOutside(false)
         dialogBinding.btnYes.setOnClickListener {
             this@ExerciesActivity.finish()
             coustomDailog.dismiss()
         }
        dialogBinding.btnNo.setOnClickListener {
            coustomDailog.dismiss()
        }
        coustomDailog.show()
    }
    private fun SetUpExerciesRecycleView(){
        binding?.rvExerciesStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciesviewAdapter= ExerciesStatusAdapter(ExerciseList!!)
        binding?.rvExerciesStatus?.adapter=exerciesviewAdapter
    }

    private fun setRestView(){
        try{
            val soundURI= Uri.parse("android.resource://com.devdroid.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping= false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }


        binding?.flRestView?.visibility= View.VISIBLE
        binding?.tvtitle?.visibility=View.VISIBLE
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.flExcerciesView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE
        binding?.tvUpcomingLable?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE
        if(restTimmer !=null){
            restTimmer?.cancel()
            restprogress=0
        }
        binding?.tvUpcomingExerciseName?.text=ExerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }
    private fun setUpExerciesView(){
        binding?.flRestView?.visibility= View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flExcerciesView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.tvUpcomingLable?.visibility=View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.INVISIBLE
        if(ExerciesRestTimmer !=null){
            ExerciesRestTimmer?.cancel()
            ExerciesRestprogress=0
        }
        speakOut(ExerciseList!![currentExercisePosition].getName())
        binding?.ivImage?.setImageResource(ExerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text=ExerciseList!![currentExercisePosition].getName()
        setRestexerciesProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.progresBar?.progress=restprogress
        restTimmer = object:CountDownTimer(restTimeDuration*10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restprogress++
                binding?.progresBar?.progress= 10 -restprogress
                binding?.tvtimmer?.text=(10 - restprogress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                ExerciseList!![currentExercisePosition].setIsSelected(true)
                exerciesviewAdapter!!.notifyDataSetChanged()
               setUpExerciesView()
            }

        }.start()


    }
    private fun setRestexerciesProgressBar(){
        binding?.progresBarExercies?.progress=ExerciesRestprogress
        ExerciesRestTimmer = object:CountDownTimer(exerciesTimeDuration*30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                ExerciesRestprogress++
                binding?.progresBarExercies?.progress= 30 - ExerciesRestprogress
                binding?.tvtimmerExercies?.text=(30 - ExerciesRestprogress).toString()
            }

            override fun onFinish() {
               if(currentExercisePosition <ExerciseList?.size!!-1){
                   ExerciseList!![currentExercisePosition].setIsSelected(false)
                   ExerciseList!![currentExercisePosition].setIsComplected(true)
                   exerciesviewAdapter!!.notifyDataSetChanged()
                  setRestView()
               }
                else{
                       finish()
                   val intent=Intent(this@ExerciesActivity,FinishActivity::class.java)
                   startActivity(intent)
               }
            }

        }.start()


    }
    override fun onDestroy() {
        super.onDestroy()
        if(restTimmer !=null){
            restTimmer?.cancel()
            restprogress=0
        }
        if(ExerciesRestTimmer !=null){
            ExerciesRestTimmer?.cancel()
            ExerciesRestprogress=0
        }
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        binding=null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            var result=tts?.setLanguage(Locale.ENGLISH)
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","LANGUAGE not support")
            }else{
                Log.e("TTS"," Initialition failed ")
            }
        }
    }
  private fun speakOut(text: String){
      tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
  }

}