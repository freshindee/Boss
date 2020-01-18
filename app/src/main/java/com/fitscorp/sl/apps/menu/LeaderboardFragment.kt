package com.fitscorp.sl.apps.menu


import android.content.Context
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
import com.fitscorp.sl.apps.menu.data.Leaderboard
import com.fitscorp.sl.apps.menu.data.LeaderboardMainData
import com.fitscorp.sl.apps.menu.vm.ContactVM
import com.fitscorp.sl.apps.menu.vm.LeaderboardVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import javax.inject.Inject




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

private var param1: String? = null
private var param2: String? = null
private var listener: InfoFragment.OnFragmentInteractionListener? = null


class LeaderboardFragment : Fragment() {

    val subscription = CompositeDisposable()

    @Inject
    lateinit var leaderboardVM: LeaderboardVM


    lateinit var contxt:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        subscription.dispose()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        App.getInstance().appComponent.inject(this)

        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerUser(view)
    }

    private fun registerUser(view:View) {

        subscription.add(leaderboardVM.getLeaderBoard().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                   val  dataObj=leaderboardVM.dataObj
                    if(dataObj!!.response.code==200){

                        val dataList=dataObj.response.dataArr.response as ArrayList
                        val mlayoutManager = LinearLayoutManager(context)
                        val leaderboardAapter = LeaderboardAapter(contxt,dataList)
                        //  otherPaymentOptionsAdapter.onitemClickListener = contxt
                        leaderboard_recycler.apply {
                            layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                            adapter = leaderboardAapter
                        }

                    }

                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }


    companion object {
        @JvmStatic
        fun newInstance(c : Context, param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    contxt=c
                }
            }
    }


}
