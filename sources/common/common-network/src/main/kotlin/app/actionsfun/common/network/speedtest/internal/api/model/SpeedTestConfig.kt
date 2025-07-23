package app.actionsfun.common.network.speedtest.internal.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister

@Root(name = "client", strict = false)
data class Client(
    @field:Attribute(name = "ip")
    var ip: String = "",

    @field:Attribute(name = "lat")
    var lat: String = "",

    @field:Attribute(name = "lon")
    var lon: String = "",

    @field:Attribute(name = "isp")
    var isp: String = "",

    @field:Attribute(name = "isprating", required = false)
    var isprating: String = "",

    @field:Attribute(name = "rating", required = false)
    var rating: String = "",

    @field:Attribute(name = "ispdlavg", required = false)
    var ispdlavg: String = "",

    @field:Attribute(name = "ispulavg", required = false)
    var ispulavg: String = "",

    @field:Attribute(name = "loggedin", required = false)
    var loggedin: String = "",

    @field:Attribute(name = "country")
    var country: String = ""
)

@Root(name = "server-config", strict = false)
data class ServerConfig(
    @field:Attribute(name = "threadcount")
    var threadcount: String = "",

    @field:Attribute(name = "ignoreids", required = false)
    var ignoreids: String = "",

    @field:Attribute(name = "notonmap", required = false)
    var notonmap: String = "",

    @field:Attribute(name = "forcepingid", required = false)
    var forcepingid: String = "",

    @field:Attribute(name = "preferredserverid", required = false)
    var preferredserverid: String = ""
)

@Root(name = "odometer", strict = false)
data class Odometer(
    @field:Attribute(name = "start")
    var start: String = "",

    @field:Attribute(name = "rate")
    var rate: String = ""
)

@Root(name = "times", strict = false)
data class Times(
    @field:Attribute(name = "dl1")
    var dl1: String = "",

    @field:Attribute(name = "dl2")
    var dl2: String = "",

    @field:Attribute(name = "dl3")
    var dl3: String = "",

    @field:Attribute(name = "ul1")
    var ul1: String = "",

    @field:Attribute(name = "ul2")
    var ul2: String = "",

    @field:Attribute(name = "ul3")
    var ul3: String = ""
)

@Root(name = "download", strict = false)
data class Download(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "initialtest", required = false)
    var initialtest: String = "",

    @field:Attribute(name = "mintestsize", required = false)
    var mintestsize: String = "",

    @field:Attribute(name = "threadsperurl", required = false)
    var threadsperurl: String = ""
)

@Root(name = "upload", strict = false)
data class Upload(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "ratio", required = false)
    var ratio: String = "",

    @field:Attribute(name = "initialtest", required = false)
    var initialtest: String = "",

    @field:Attribute(name = "mintestsize", required = false)
    var mintestsize: String = "",

    @field:Attribute(name = "threads", required = false)
    var threads: String = "",

    @field:Attribute(name = "maxchunksize", required = false)
    var maxchunksize: String = "",

    @field:Attribute(name = "maxchunkcount", required = false)
    var maxchunkcount: String = "",

    @field:Attribute(name = "threadsperurl", required = false)
    var threadsperurl: String = ""
)

@Root(name = "latency", strict = false)
data class Latency(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "waittime", required = false)
    var waittime: String = "",

    @field:Attribute(name = "timeout", required = false)
    var timeout: String = ""
)

@Root(name = "socket-download", strict = false)
data class SocketDownload(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "initialthreads", required = false)
    var initialthreads: String = "",

    @field:Attribute(name = "minthreads", required = false)
    var minthreads: String = "",

    @field:Attribute(name = "maxthreads", required = false)
    var maxthreads: String = "",

    @field:Attribute(name = "threadratio", required = false)
    var threadratio: String = "",

    @field:Attribute(name = "maxsamplesize", required = false)
    var maxsamplesize: String = "",

    @field:Attribute(name = "minsamplesize", required = false)
    var minsamplesize: String = "",

    @field:Attribute(name = "startsamplesize", required = false)
    var startsamplesize: String = "",

    @field:Attribute(name = "startbuffersize", required = false)
    var startbuffersize: String = "",

    @field:Attribute(name = "bufferlength", required = false)
    var bufferlength: String = "",

    @field:Attribute(name = "packetlength", required = false)
    var packetlength: String = "",

    @field:Attribute(name = "readbuffer", required = false)
    var readbuffer: String = ""
)

@Root(name = "socket-upload", strict = false)
data class SocketUpload(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "initialthreads", required = false)
    var initialthreads: String = "",

    @field:Attribute(name = "minthreads", required = false)
    var minthreads: String = "",

    @field:Attribute(name = "maxthreads", required = false)
    var maxthreads: String = "",

    @field:Attribute(name = "threadratio", required = false)
    var threadratio: String = "",

    @field:Attribute(name = "maxsamplesize", required = false)
    var maxsamplesize: String = "",

    @field:Attribute(name = "minsamplesize", required = false)
    var minsamplesize: String = "",

    @field:Attribute(name = "startsamplesize", required = false)
    var startsamplesize: String = "",

    @field:Attribute(name = "startbuffersize", required = false)
    var startbuffersize: String = "",

    @field:Attribute(name = "bufferlength", required = false)
    var bufferlength: String = "",

    @field:Attribute(name = "packetlength", required = false)
    var packetlength: String = "",

    @field:Attribute(name = "disabled", required = false)
    var disabled: String = ""
)

@Root(name = "socket-latency", strict = false)
data class SocketLatency(
    @field:Attribute(name = "testlength")
    var testlength: String = "",

    @field:Attribute(name = "waittime", required = false)
    var waittime: String = "",

    @field:Attribute(name = "timeout", required = false)
    var timeout: String = ""
)

@Root(name = "settings", strict = false)
data class SpeedtestConfig(
    @field:Element(name = "client")
    var client: Client = Client(),

    @field:Element(name = "server-config")
    var serverConfig: ServerConfig = ServerConfig(),

    @field:Element(name = "licensekey", required = false)
    var licensekey: String = "",

    @field:Element(name = "customer", required = false)
    var customer: String = "",

    @field:Element(name = "odometer", required = false)
    var odometer: Odometer = Odometer(),

    @field:Element(name = "times", required = false)
    var times: Times = Times(),

    @field:Element(name = "download", required = false)
    var download: Download = Download(),

    @field:Element(name = "upload", required = false)
    var upload: Upload = Upload(),

    @field:Element(name = "latency", required = false)
    var latency: Latency = Latency(),

    @field:Element(name = "socket-download", required = false)
    var socketDownload: SocketDownload = SocketDownload(),

    @field:Element(name = "socket-upload", required = false)
    var socketUpload: SocketUpload = SocketUpload(),

    @field:Element(name = "socket-latency", required = false)
    var socketLatency: SocketLatency = SocketLatency()
) {
    companion object {

        fun parseXml(xml: String): SpeedtestConfig {
            return Persister().read(SpeedtestConfig::class.java, xml)
        }
    }
}