package com.example.sindhuviewmodel.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sindhuviewmodel.R
import com.example.sindhuviewmodel.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private var score = 0
    private var currentWordCount = 0
    private var currentScrambledWord = "test"


    // Membinding objek dengan akses view di didalam layout game fragment layout
    private lateinit var binding: GameFragmentBinding

    // membuat ViewModel saat fragmen dibuat dan jika fragmen dibuat ulang,
    // maka viewmodel menerima instance GameViewModel yang sama yang dibuat oleh fragmen pertama
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        updateNextWordOnScreen()
        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(
            R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    /*
    * Memeriksa kata pengguna/user, dan memperbarui skor yang sesuai.
    * Selanjutnya akan menampilkan kata acak berikutnya jika di tekan button submit.
    */
    private fun onSubmitWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        score += SCORE_INCREASE
        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
        binding.score.text = getString(R.string.score, score)
        setErrorTextField(false)
        updateNextWordOnScreen()
    }


    private fun onSkipWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /*
     * Mendapatkan kata acak untuk daftar kata dan mengacak huruf di dalamnya.
     */
    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }

    /*
     * Inisialisasi ulang data dalam ViewModel kemudian memperbarui tampilan dengan data baru, untuk
     memulai ulang permainan.
     */
    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Menyetel dan mereset ulang status kesalahan teks.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * tampilan layar scrambled selanjutnya.
     */
    private fun updateNextWordOnScreen() {
        binding.textViewUnscrambledWord.text = currentScrambledWord
    }
}