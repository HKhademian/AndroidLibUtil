package ir.hco.util.page

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(val app: Application) : AndroidViewModel(app)
