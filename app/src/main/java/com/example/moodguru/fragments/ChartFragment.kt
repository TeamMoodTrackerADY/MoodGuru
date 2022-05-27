package com.example.moodguru.fragments

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.moodguru.R
import com.example.moodguru.parseDataModel.Chart
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.*


class ChartFragment : Fragment() {
    lateinit var graphView: GraphView

    companion object {
        const val TAG = "ChartFragment"
        var minX = "empty"
        var maxX = "empty"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var ratingMap = generateRatingMap(365)
        var toRecent = false
        generateLineGraph(view, ratingMap, toRecent)
        toRecent = !toRecent

        val moveBtn = view.findViewById<Button>(R.id.moveBtn)
        moveBtn.setOnClickListener {
            generateLineGraph(view, ratingMap, toRecent)
            if (toRecent){
                moveBtn.text = "Back To Start"
            }else{
                moveBtn.text = "Go To Recent"
            }
            toRecent = !toRecent
        }
    }

    private fun generateLineGraph(view: View, ratingMap: MutableMap<Date, Double>, toRecent: Boolean){
        // generate Dates
        var dateList = mutableListOf<Date>()
        dateList.clear()
        ratingMap.forEach { entry ->
            dateList.add(entry.key)
        }
        dateList.sort()
        for (date: Date in dateList){
            Log.i(TAG, date.toString())
        }
        // each point on our x and y axis.
        val series: LineGraphSeries<DataPoint> = LineGraphSeries()
        for (date: Date in dateList){
            var dataPoint = DataPoint(date, ratingMap.get(date)!!)
            series.appendData(dataPoint, true, ratingMap.size)
            Log.i("graph", "a datapoint added")
        }

        graphView = view.findViewById(R.id.idGraphView)
        graphView.removeAllSeries()
        val glr: GridLabelRenderer = graphView.getGridLabelRenderer()
        glr.padding = 32 // should allow for 3 digits to fit on screen
        glr.gridColor = Color.BLACK
        glr.textSize = 40f

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(DateAsXAxisLabelFormatter(getActivity()))
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3)


        // set manual x and y bounds to have nice steps
        graphView.getViewport().setXAxisBoundsManual(true)
        graphView.getViewport().setMinX(dateList[0].getTime().toDouble())
        graphView.getViewport().setMaxX(dateList[dateList.size-1].getTime().toDouble()/2+dateList[0].getTime().toDouble()/2)
        graphView.getViewport().setScrollable(true)
        Log.i(TAG, "size: " + dateList.size + ", x values: " + dateList[0].toString() + " and " + dateList[dateList.size-1].toString())

        graphView.getViewport().setYAxisBoundsManual(true)
        graphView.getViewport().setMinY(-1.0)
        graphView.getViewport().setMaxY(1.0)
        graphView.getViewport().setScrollable(true)

        if (toRecent){
            graphView.getViewport().setMinX(dateList[0].getTime().toDouble()/2 + dateList[dateList.size-1].getTime().toDouble()/2)
            graphView.getViewport().setMaxX(dateList[dateList.size-1].getTime().toDouble())
            graphView.getViewport().setScrollable(true)
        }


        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graphView.getGridLabelRenderer().setHumanRounding(false)

        series.setDrawDataPoints(true)
        series.setAnimated(true)
        series.setColor(Color.rgb(255, 170, 40))
        series.setDataPointsRadius(20f)
        series.setThickness(10)

        // add data series to graph view.
        graphView.addSeries(series)
    }

    private fun generateRatingMap(maxDay: Int): MutableMap<Date, Double>{
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        formatter.parse(formatter.format(Date()))
        var ratingMap = mutableMapOf<Date, Double>()
        var dayCount = 0
        // Specify which class to query
        val cq: ParseQuery<Chart> = ParseQuery.getQuery(Chart::class.java)
        cq.include(Chart.KEY_USER)
            .include(Chart.KEY_DATE)
            .include(Chart.KEY_DDATE)
            .include(Chart.KEY_AVGRATING)
            .include(Chart.KEY_POSTCOUNT)
        cq.whereEqualTo(Chart.KEY_USER, ParseUser.getCurrentUser())
            .addDescendingOrder("dateTypeDate")
        var charts = cq.find()
        for (chart: Chart in charts){
            if (dayCount == maxDay){
                break
            }else{
                dayCount++
            }
            Log.i(TAG, "Date: " + chart.getDate() + ", avg rating: " + chart.getAvgRating())
            ratingMap.put(formatter.parse(formatter.format(chart.getDDate())), chart.getAvgRating()!!.toDouble())
        }
        return ratingMap
    }
}