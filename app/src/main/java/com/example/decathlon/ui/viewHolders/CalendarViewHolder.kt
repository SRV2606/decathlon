package com.example.decathlon.ui.viewHolders

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.calendar.formatDateToDayOfMonth
import com.example.decathlon.R
import com.example.decathlon.base.BaseViewHolder
import com.example.decathlon.databinding.LayoutCalendarDayBinding
import com.example.domain.models.CalendarDay

class CalendarViewHolder(
    private val binding: LayoutCalendarDayBinding,
    private val context: Context
) : BaseViewHolder<CalendarDay>(binding) {


    override fun setItem(data: CalendarDay?, itemClickListener: ((CalendarDay) -> Unit)?) {
        val day = formatDateToDayOfMonth(data?.date.toString())
        binding.dayOfWeekTextView.text = day

        data?.let { calendarDay ->
            if (!calendarDay.isCurrentMonth) {
                val color = ContextCompat.getColor(context, R.color.purple_200)
                binding.dayOfWeekTextView.setTextColor(color)

            } else {
                val color = ContextCompat.getColor(context, R.color.black)
                binding.dayOfWeekTextView.setTextColor(color)

                binding.dayOfWeekTextView.setOnClickListener {
                    itemClickListener?.let {
                        it(data)
                    }
                }

            }
        }

    }
}


