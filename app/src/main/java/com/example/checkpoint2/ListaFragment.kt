package com.example.checkpoint2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.checkpoint2.databinding.FragmentListaBinding

class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArrayAdapter<Pessoa>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atualizarLista()
    }

    private fun atualizarLista() {
        adapter = object : ArrayAdapter<Pessoa>(requireContext(), android.R.layout.simple_list_item_2, android.R.id.text1, DataRepository.pessoas) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = LayoutInflater.from(context)
                val rowView = convertView ?: inflater.inflate(R.layout.item_lista, parent, false)

                val textViewNome = rowView.findViewById<TextView>(R.id.textViewNome)
                val buttonExcluir = rowView.findViewById<Button>(R.id.buttonExcluir)

                val pessoa = DataRepository.pessoas[position]
                textViewNome.text = "${pessoa.nome} ${pessoa.sobrenome}"

                // Configuração do botão Excluir
                buttonExcluir.setOnClickListener {
                    DataRepository.pessoas.removeAt(position)
                    atualizarLista()
                    Toast.makeText(context, "Registro excluído", Toast.LENGTH_SHORT).show()
                }

                // Configuração do clique para editar
                rowView.setOnClickListener {
                    val fragment = CadastroFragment()
                    fragment.arguments = Bundle().apply {
                        putInt("position", position)
                        putString("nome", pessoa.nome)
                        putString("sobrenome", pessoa.sobrenome)
                        putString("email", pessoa.email)
                        putString("telefone", pessoa.telefone)
                        putString("endereco", pessoa.endereco)
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                return rowView
            }
        }
        binding.listViewPessoas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
