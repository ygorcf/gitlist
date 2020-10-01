package com.example.gitlist.app.activity.githubRepositoryList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gitlist.R
import com.example.gitlist.app.listAdapter.GithubRepositoryAdapter
import com.example.gitlist.app.listAdapter.listener.EndlessRecyclerViewListener
import com.example.gitlist.model.GithubRepository
import kotlinx.android.synthetic.main.activity_github_repository_list.*

/**
 * Classe de apresentar os dados da activity de listar repositorios.
 *
 * @property filterVisibility A visibilidade dos campos de filtro.
 * @property loading Se a tela esta carregando.
 */
class GithubRepositoryListActivity : AppCompatActivity(), GithubRepositoryListMVP.View {

    override var endlessRecyclerViewListener: EndlessRecyclerViewListener? = null

    override var filterVisibility: Boolean
        get() {
            layout_filter?.measure(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
            return layout_filter?.height == layout_filter?.measuredHeight
        }
        set(value) {
            if (layout_filter != null) {
                layout_filter?.measure(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT)
                layout_filter?.visibility = View.VISIBLE
                if (value) {
                    layout_filter?.layoutParams?.height = 0
                    FilterAnimation.startHeightAnimation(layout_filter!!,
                        0,
                        layout_filter?.measuredHeight ?: 0)
                } else {
                    FilterAnimation.startHeightAnimation(layout_filter!!,
                        layout_filter?.measuredHeight ?: 0,
                        0) {
                        layout_filter?.visibility = View.GONE
                    }
                }
            }
        }

    override var loading: Boolean
        get() = swipe_layout_github_repository?.isRefreshing == true
        set(value) {
            swipe_layout_github_repository?.isRefreshing = value
        }

    private lateinit var listAdapter: GithubRepositoryAdapter

    /**
     * Metodo para apresentar a lista de repositorios.
     *
     * @param repositories A lista de repositorios a serem apresentados.
     */
    override fun showRepositories(repositories: List<GithubRepository>) {
        this.listAdapter.addRepositories(repositories)
    }

    override fun setStartFilter(filter: String) {
        input_filter?.setText(filter)
    }

    // Metodos do ciclo de vida do android.

    /**
     * Metodo do ciclo de vida do android de quando foi criada a activity.
     *
     * @param savedInstanceState Os dados de estado salvos da ultima instancia.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repository_list)

        input_filter?.addTextChangedListener(createTextWatcher {
            presenter?.onFilterTextChange(it)
        })
        ArrayAdapter.createFromResource(this,
            R.array.github_repository_options_sort,
            android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_sort?.adapter = this
            spinner_sort?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long) {
                    presenter?.onSortTextChange(when (position) {
                        0 -> "stars"
                        1 -> "forks"
                        2 -> "help-wanted-issues"
                        else -> null
                    })
                }

            }
        }
        swipe_layout_github_repository?.setColorSchemeResources(R.color.colorPrimary)
        swipe_layout_github_repository?.setOnRefreshListener {
            presenter?.onSwipeRefresh()
        }

        if (github_repository_list != null) {
            this.listAdapter = GithubRepositoryAdapter(endlessRecyclerViewListener).also {
                it.bindToListView(github_repository_list, this)
            }
        }

        if (presenter == null) {
            presenter = GithubRepositoryListPresenter()
        }

        presenter!!.view = this
        presenter!!.onActivityCreated()
    }

    /**
     * Metodo do ciclo de vida do android de quando o menu de opcoes e apresentado pela primeira vez.
     *
     * @param menu O menu apresentado.
     * @return Se o menu deve ser apresentado.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.repository_list_options_menu, menu)
        return true
    }

    /**
     * Metodo de callback de quando e clicado em um item do menu de opcoes da activity. Com o
     * objetivo de chamar os metodos do presenter.
     *
     * @param item O item clicado do menu.
     * @return Retorna false se deve continuar a execução padrao do callback.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.option_filter) {
            presenter?.onFilterButtonClicked()
        }
        return true
    }

    /**
     * Metodo para obter um ouvinte de mudancas em campos de texto. Com o objetivo de encapsular o
     * ouvinte base do android.
     *
     * @param onChangeListener Um ouvinte simples em formato kotlin de mudancas no campo de texto.
     */
    private fun createTextWatcher(onChangeListener: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    onChangeListener.invoke(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    companion object {
        /**
         * A instancia da classe responsavel pelas regras de negocio da tela de listagem de
         * repositorios.
         */
        private var presenter: GithubRepositoryListMVP.Presenter? = null
    }

}
