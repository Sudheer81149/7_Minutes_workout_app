package com.devdroid.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devdroid.a7minutesworkout.databinding.ActivityHistoryactivityBinding
import kotlinx.coroutines.launch

class Historyactivity : AppCompatActivity() {
    private  var binding:ActivityHistoryactivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityHistoryactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar !=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY"
        }
         binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
             onBackPressed()
         }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
    }
    private fun getAllCompletedDates(historyDao: HistoryDao) {


        lifecycleScope.launch {
            historyDao.fetchALlDates().collect { allCompletedDatesList->
                // List items are printed in log.
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.tvHistory?.visibility=View.VISIBLE
                    binding?.rvHistory?.visibility=View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility=View.INVISIBLE
                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@Historyactivity)
                    val dates =ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val histroyAdapter=HistoryAdapter(dates)
                    binding?.rvHistory?.adapter=histroyAdapter
                }else{
                    binding?.tvHistory?.visibility=View.GONE
                    binding?.rvHistory?.visibility=View.GONE
                    binding?.tvNoDataAvailable?.visibility=View.VISIBLE
                }
            }
        }

    }
    // END
    override fun onDestroy() {
        super.onDestroy()
// reset the binding to null to avoid memory leak
        binding = null
    }
}