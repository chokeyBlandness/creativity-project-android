package usst.dysrc.kaoyan.singleCalendar

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.single_calendar_frame_layout.view.*
import usst.dysrc.kaoyan.R
import usst.dysrc.kaoyan.utils.DateAdapter
import usst.dysrc.kaoyan.utils.DateUtil
import usst.dysrc.kaoyan.utils.SingleCalendarTransaction
import java.util.*


class SingleCalendar: LinearLayout {
    private var dateAdapter: DateAdapter? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }


    fun init(context: Context) {
        val calendar=Calendar.getInstance()
        val view=LayoutInflater.from(context).inflate(R.layout.single_calendar_frame_layout,this)
        renderView(calendar)
        pre_month_button.setOnClickListener {
            calendar!!.add(Calendar.MONTH, -1)//change to past month
            renderView(calendar)
        }
        next_month_button.setOnClickListener {
            calendar!!.add(Calendar.MONTH,1)//change to next month
            renderView(calendar)
        }
    }

    /**
     * render views to show year , month and the month's date
     * @param manipulateCalendar the calendar of the month will be rendered
     */

    private fun renderView(manipulateCalendar: Calendar){
        year_and_month_text_view.text = (DateUtil.getCalendarYear(manipulateCalendar).toString()
                + "-" + (DateUtil.getCalendarMonth(manipulateCalendar) + 1))
        dateAdapter= DateAdapter(context,manipulateCalendar)
        date_grid_view.adapter=dateAdapter

    }

    fun setCalendarTransaction(singleCalendarTransaction: SingleCalendarTransaction){
        this.dateAdapter!!.setCalendarTransaction(singleCalendarTransaction)
    }
}

