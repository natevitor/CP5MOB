package com.example.checkpoint2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.checkpoint2.databinding.FragmentCadastroBinding

class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurando o Spinner com algumas opções
        val categorias = arrayOf("Categoria A", "Categoria B", "Categoria C")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategoria.adapter = adapter

        // Preencher os campos se houver dados nos argumentos (para edição)
        arguments?.let {
            val position = it.getInt("position")
            val nome = it.getString("nome", "")
            val sobrenome = it.getString("sobrenome", "")
            val email = it.getString("email", "")
            val telefone = it.getString("telefone", "")
            val endereco = it.getString("endereco", "")

            binding.inputNome.setText(nome)
            binding.inputSobrenome.setText(sobrenome)
            binding.inputEmail.setText(email)
            binding.inputTelefone.setText(telefone)
            binding.inputEndereco.setText(endereco)

            binding.buttonSalvar.setOnClickListener {
                val updatedNome = binding.inputNome.text.toString()
                val updatedSobrenome = binding.inputSobrenome.text.toString()
                val updatedEmail = binding.inputEmail.text.toString()
                val updatedTelefone = binding.inputTelefone.text.toString()
                val updatedEndereco = binding.inputEndereco.text.toString()

                if (updatedNome.isNotEmpty() && updatedSobrenome.isNotEmpty() && updatedEmail.isNotEmpty() &&
                    updatedTelefone.isNotEmpty() && updatedEndereco.isNotEmpty()) {

                    DataRepository.pessoas[position] = Pessoa(updatedNome, updatedSobrenome, updatedEmail, updatedTelefone, updatedEndereco)

                    Toast.makeText(context, "Cadastro atualizado com sucesso", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            // Lógica do botão salvar para um novo cadastro
            binding.buttonSalvar.setOnClickListener {
                val nome = binding.inputNome.text.toString()
                val sobrenome = binding.inputSobrenome.text.toString()
                val email = binding.inputEmail.text.toString()
                val telefone = binding.inputTelefone.text.toString()
                val endereco = binding.inputEndereco.text.toString()

                if (nome.isNotEmpty() && sobrenome.isNotEmpty() && email.isNotEmpty() &&
                    telefone.isNotEmpty() && endereco.isNotEmpty()) {

                    // Adiciona uma nova pessoa ao repositório
                    DataRepository.pessoas.add(Pessoa(nome, sobrenome, email, telefone, endereco))

                    Toast.makeText(context, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()

                    // Navega para o fragmento de lista após salvar
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ListaFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
