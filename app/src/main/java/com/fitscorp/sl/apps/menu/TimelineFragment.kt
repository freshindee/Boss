package com.fitscorp.sl.apps.menu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.App

import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.menu.adapter.LeaderboardAapter
import com.fitscorp.sl.apps.menu.adapter.TimelineAdapter
import com.fitscorp.sl.apps.menu.vm.LeaderboardVM
import com.fitscorp.sl.apps.menu.vm.TimelineVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TimelineFragment : Fragment() {

    val subscription = CompositeDisposable()


    @Inject
    lateinit var timelineVM: TimelineVM


    lateinit var contxt:Context


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onDestroy() {
        super.onDestroy()
        subscription.dispose()
    }


   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    App.getInstance().appComponent.inject(this)

    return inflater.inflate(R.layout.fragment_timeline, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerUser(view)
    }


    private fun registerUser(view:View) {

        subscription.add(timelineVM.getTimeLineStore().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                    val  dataObj=timelineVM.dataObj
                    if(dataObj!!.response.code==200){

                        val dataList=dataObj.response.dataArr as ArrayList
                        val mlayoutManager = LinearLayoutManager(context)
                        val timelineAapter = TimelineAdapter(contxt,dataList)
                        //  otherPaymentOptionsAdapter.onitemClickListener = contxt
                        timeline_recycle.apply {
                            layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                            adapter = timelineAapter
                        }

                    }

                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimelineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(c:Context,param1: String, param2: String) =
            TimelineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    contxt=c
                }
            }
    }
}
