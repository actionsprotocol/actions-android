package app.actionsfun.common.network.connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface NetworkConnectionObserver {

    val connection: Flow<Network>
}

class NetworkConnectionObserverImpl(
    context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : NetworkConnectionObserver {

    override val connection = MutableSharedFlow<Network>(replay = 1)

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            scope.launch {
                connection.emit(network)
            }
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            scope.launch {
                connection.emit(network)
            }
        }

        @SuppressLint("MissingPermission")
        override fun onLost(network: Network) {
            super.onLost(network)
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork != null) {
                scope.launch {
                    connection.emit(activeNetwork)
                }
            }
        }
    }

    init {
        observeConnection()
            .onEach(connection::emit)
            .launchIn(scope)
    }

    @SuppressLint("MissingPermission")
    private fun observeConnection(): Flow<Network> = MutableSharedFlow<Network>().also { flow ->
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        connectivityManager.activeNetwork?.let { network ->
            scope.launch {
                flow.emit(network)
            }
        }
    }
}
