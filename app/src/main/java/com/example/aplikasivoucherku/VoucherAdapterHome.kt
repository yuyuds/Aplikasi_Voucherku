package com.example.aplikasivoucherku

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasivoucherku.DatabaseHelper
import com.example.aplikasivoucherku.FormEditActivity
import com.example.aplikasivoucherku.R
import com.example.aplikasivoucherku.model.VoucherModel



class VoucherAdapterHome(private val list:ArrayList<VoucherModel>):
    RecyclerView.Adapter<VoucherAdapterHome.VoucherViewHolder>(){


    inner class VoucherViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val textName : TextView
        val textHarga : TextView
        val imageVoucher : ImageView
        val btnBeli: Button


        init{
            textName = v.findViewById(R.id.textNamaVoucher)
            textHarga = v.findViewById(R.id.textHargaVoucher)
            imageVoucher = v.findViewById(R.id.imageVoucher)
            btnBeli = v.findViewById(R.id.buttonBeli)


        }


        fun bind(data : VoucherModel){
            val id:Int = data.id
            val nama:String = data.name
            val harga:Int = data.price
            val gambar: Bitmap = data.image

            textName.text = nama
            textHarga.text = harga.toString()
            imageVoucher.setImageBitmap(gambar)

            btnBeli.setOnClickListener {
//                    Toast.makeText(btnEditVoucher.getContext(), "id" + data.id, Toast.LENGTH_SHORT).show()
                val intent = Intent(btnBeli.context, PembelianActivity::class.java)
                btnBeli.context.startActivity(intent)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherAdapterHome.VoucherViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_voucher_home,
            parent, false)

        return VoucherViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.bind(list[position])

    }



}