package com.ifechukwu.tlvparser.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ifechukwu.tlvparser.R
import com.ifechukwu.tlvparser.data.ParserState
import com.ifechukwu.tlvparser.databinding.ActivityTlvBinding
import kotlinx.coroutines.flow.collectLatest

class TLVActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTlvBinding
    private lateinit var tlvAdapter: TlvAdapter
    private lateinit var viewModel: TlvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTlvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TlvViewModel::class.java]

        initViews()
        observeParseFlow()
    }

    private fun initViews() {
        tlvAdapter = TlvAdapter()

        binding.rvTlv.apply {
            layoutManager = LinearLayoutManager(this@TLVActivity)
            addItemDecoration(DividerItemDecoration(this@TLVActivity, DividerItemDecoration.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = tlvAdapter
        }

        binding.btnParse.setOnClickListener {
            val tlv = binding.edtEnterTlv.text.toString()
            if (tlv.isEmpty()) {
                Snackbar.make(binding.root,
                    getString(R.string.please_enter_a_valid_tlv), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            viewModel.parseTlv(tlv)
        }
    }

    private fun observeParseFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.parserStateFlow.collectLatest { result ->
                when (result) {
                    is ParserState.Idle -> {
                        binding.progressBar.isVisible = false
                    }
                    is ParserState.Error -> {
                        tlvAdapter.submitList(emptyList())
                        binding.progressBar.isVisible = false

                        showAlertDialog(message = result.message)
                    }
                    is ParserState.Parsing -> {
                        binding.progressBar.isVisible = true
                    }
                    is ParserState.Success -> {
                        binding.progressBar.isVisible = false

                        tlvAdapter.submitList(result.tags)
                    }
                }
            }
        }
    }

    private fun showAlertDialog(
        title: String? = getString(R.string.app_name),
        message: String,
        positiveTitle: String? = getString(R.string.okay),
    ) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(
                positiveTitle
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                }
            }
        builder.create()
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tlv_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_data_1 -> {
                viewModel.parseTlv(getString(R.string.tlv_example))
                binding.edtEnterTlv.setText(getString(R.string.tlv_example))
                return true
            }
            R.id.action_data_2 -> {
                viewModel.parseTlv(getString(R.string.tlv_example_2))
                binding.edtEnterTlv.setText(getString(R.string.tlv_example_2))
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}