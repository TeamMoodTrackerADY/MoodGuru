package com.example.moodguru.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodguru.EmotionAdapter
import com.example.moodguru.R
import com.example.moodguru.parseDataModel.Emotion
import com.parse.ParseQuery


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionFragment : Fragment() {

    lateinit var rvEmotions: RecyclerView
    lateinit var emotionAdapter: EmotionAdapter
    val emotionList: MutableList<Emotion> = mutableListOf()

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emotionAdapter = EmotionAdapter(requireContext(), emotionList, object : EmotionAdapter.OnSelectHandler{
            override fun onSelect(emotion: Emotion) {
//                Toast.makeText(requireContext(), emotion.getAdjective() + " is selected", Toast.LENGTH_SHORT).show()
                setFragmentResult(ComposeFragment.KEY_REQUEST_EMO, bundleOf(KEY_SELECT_EMO to emotion))
            }

        })
        populateEmotionList()
    }

    private fun populateEmotionList() {
        // Define the class we would like to query
        val query: ParseQuery<Emotion> = ParseQuery.getQuery(Emotion::class.java)
        // TODO: Define our query conditions
        query.findInBackground{list, e ->
            if (e != null){
                Log.e(TAG, "populateEmotionList failed", e)
                e.printStackTrace()
            } else{
                if (list != null){
                    emotionList.clear()
                    emotionList.addAll(list)
                    emotionAdapter.notifyDataSetChanged()
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emotion, container, false)
    }

    companion object {
        val TAG = "EmotionFragment"
        val KEY_SELECT_EMO = "selectEmotion"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param priority: Priority that corresponds to a color
         * @return A new instance of fragment PriorityFragment.
         */
        @JvmStatic
//        fun newInstance() = PriorityDialogFragment()
        fun newInstance() = EmotionFragment().apply {
            arguments = Bundle().apply {
//                putParcelable(KEY_SELECT_EMO, emotion)
            }
        }

        // TODO: emotion dialog not show itself, violate SRP
//        fun showEmotionFragment(fragmentManager: FragmentManager) {
//            val emotionFragment = newInstance()
//            emotionFragment.show(fragmentManager, TAG)
//        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        setWidthPercent()
//    }

//    fun setWidthPercent() {
//        val percent = 0.85
//        val dm = Resources.getSystem().displayMetrics
//        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
//        val percentWidth = rect.width() * percent
//        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvEmotions = view.findViewById(R.id.rvEmotions)
        rvEmotions.layoutManager = GridLayoutManager(requireContext(), 2)
        rvEmotions.adapter = emotionAdapter

    }


}