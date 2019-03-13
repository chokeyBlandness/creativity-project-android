package usst.dysrc.kaoyan.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import usst.dysrc.kaoyan.R
import java.util.*

class DateAdapter: BaseAdapter {
    private val calendar: Calendar
    val context: Context
    var singleCalendarTransaction: SingleCalendarTransaction? = null

    /**
     * constructor function with a context parameter
     * @param context the context would be injected
     */
    constructor(context: Context,calendar: Calendar){
        this.context = context
        this.calendar = calendar
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertView1:View=LayoutInflater.from(context).inflate(R.layout.single_calendar_component_layout, null)
//        if (convertView==null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.single_calendar_component_layout, null)
//        }
        val date= Date(DateUtil.getCalendarYear(calendar),
                DateUtil.getCalendarMonth(calendar),
                getItem(position) as Int)
        val single_calendar_text_view: TextView = convertView1!!.findViewById(R.id.single_calendar_text_view)
        val single_calendar_icon_image_view: ImageView = convertView1.findViewById(R.id.single_calendar_icon_image_view)
        if (position<DateUtil.getFirstDayOfMonth(calendar)-1){
            single_calendar_text_view.visibility=View.INVISIBLE
            single_calendar_icon_image_view.visibility=View.GONE
        }else{
            single_calendar_text_view.visibility=View.VISIBLE
            single_calendar_text_view.text=getItem(position).toString()
            if (singleCalendarTransaction != null) {
                singleCalendarTransaction!!.manipulateTextView(date,single_calendar_text_view)
                singleCalendarTransaction!!.manipulateIconView(date,single_calendar_icon_image_view)
            }
        }
        return convertView1
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return The data at the specified position.
     */
    override fun getItem(position: Int): Any {
        return if (position<DateUtil.getFirstDayOfMonth(calendar)-1)
            0
        else
            position+1-(DateUtil.getFirstDayOfMonth(calendar)-1)
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    override fun getCount(): Int {
        return DateUtil.getCalendarMonthLastDay(calendar) + DateUtil.getFirstDayOfMonth(calendar) - 1
    }

    fun setCalendarTransaction(singleCalendarTransaction: SingleCalendarTransaction){
        this.singleCalendarTransaction=singleCalendarTransaction
    }
}