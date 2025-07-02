

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alquilervehiculos.data.model.Vehiculo
import com.example.alquilervehiculos.databinding.VehiculoListItemBinding

class VehiculoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Vehiculo, VehiculoAdapter.VehiculoViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(vehiculoId: Int)
        fun onItemEditClick(vehiculoId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val binding = VehiculoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehiculoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val currentVehiculo = getItem(position)
        holder.bind(currentVehiculo)
    }

    inner class VehiculoViewHolder(private val binding: VehiculoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position).vehiculoId)
                }
            }
            binding.btnEdit.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemEditClick(getItem(position).vehiculoId)
                }
            }
        }


        fun bind(vehiculo: Vehiculo) {
            binding.tvMarca.text = vehiculo.marca
            binding.tvModelo.text = vehiculo.modelo
            binding.tvPlaca.text = vehiculo.placa
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Vehiculo>() {
        override fun areItemsTheSame(oldItem: Vehiculo, newItem: Vehiculo) =
            oldItem.vehiculoId == newItem.vehiculoId

        override fun areContentsTheSame(oldItem: Vehiculo, newItem: Vehiculo) =
            oldItem == newItem
    }
}