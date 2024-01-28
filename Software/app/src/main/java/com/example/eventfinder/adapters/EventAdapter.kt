package com.example.eventfinder.adapters

import com.example.eventfinder.RedirectActivity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.R
import com.example.eventfinder.ReviewActivity
import com.example.eventfinder.entities.Event
import java.util.Locale

class EventAdapter(private val eventsList: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.ENGLISH)
        private val taskName: TextView
        private val taskDueDate: TextView
        private val taskCategoryColor: SurfaceView
        private val  buttonRedirect : Button
        private val  buttonReview: Button

        init {
            taskName = view.findViewById(R.id.tv_task_name)
            taskDueDate = view.findViewById(R.id.tv_task_due_date)
            taskCategoryColor = view.findViewById(R.id.sv_task_category_color)
            buttonRedirect = view.findViewById(R.id.buttonRedirect)
            buttonReview = view.findViewById(R.id.buttonReview)

        private val taskName: TextView = view.findViewById(R.id.tv_task_name)
        private val taskDueDate: TextView = view.findViewById(R.id.tv_task_due_date)
        private val taskCategoryColor: SurfaceView = view.findViewById(R.id.sv_task_category_color)
        private val buttonRedirect: Button = view.findViewById(R.id.buttonRedirect)
        private val imageViewFavorite: ImageView = view.findViewById(R.id.imageViewFavorite)
        private val  buttonReview: Button = view.findViewById(R.id.buttonReview)

        init {
            buttonRedirect.setOnClickListener {
                val event = eventsList[adapterPosition]
                val intent = Intent(itemView.context, RedirectActivity::class.java)
                intent.putExtra("urlOrganizer", event.urlOrganizer)
                itemView.context.startActivity(intent)
            }

            buttonReview.setOnClickListener {
                val event = eventsList[adapterPosition]
                val intent = Intent(itemView.context, ReviewActivity::class.java)
                intent.putExtra("eventId", event.id)
                itemView.context.startActivity(intent)
            }

            imageViewFavorite.setOnClickListener {
                val event = eventsList[adapterPosition]
                event.isFavorite = !event.isFavorite
                updateFavoriteImage(event.isFavorite)
                updateEvent(event);
            }
            buttonReview.setOnClickListener {
                val event = eventsList[adapterPosition]
                val intent = Intent(itemView.context, ReviewActivity::class.java)
                intent.putExtra("eventId", event.id)
                Log.d("Evemt1","Rvem : ${event.id}")
                itemView.context.startActivity(intent)
            }

        }

        fun bind(event: Event) {
            taskName.text = event.name
            taskDueDate.text = sdf.format(event.date)
            taskCategoryColor.setBackgroundColor(event.category.color.toColorInt())
            updateFavoriteImage(event.isFavorite)
        }

        private fun updateEvent(event: Event) {
            DatabaseAPP.getInstance().getEventsDao().updateEvent(event)
        }
        private fun updateFavoriteImage(isFavorite: Boolean) {
            val drawableResource = if (isFavorite)
                R.drawable.baseline_turned_in_24_yellow
            else
                R.drawable.baseline_turned_in_24_grey

            imageViewFavorite.setImageResource(drawableResource)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val taskView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)

        return EventViewHolder(taskView)
    }

    override fun getItemCount() = eventsList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventsList[position])
    }

}