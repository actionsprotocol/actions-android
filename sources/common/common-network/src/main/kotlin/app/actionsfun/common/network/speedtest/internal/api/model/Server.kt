package app.actionsfun.common.network.speedtest.internal.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister

@Root(name = "server", strict = false)
data class Server(
    @field:Attribute(name = "url")
    var url: String = "",

    @field:Attribute(name = "lat")
    var lat: String = "",

    @field:Attribute(name = "lon")
    var lon: String = "",

    @field:Attribute(name = "name")
    var name: String = "",

    @field:Attribute(name = "country")
    var country: String = "",

    @field:Attribute(name = "cc")
    var cc: String = "",

    @field:Attribute(name = "sponsor")
    var sponsor: String = "",

    @field:Attribute(name = "id")
    var id: String = "",

    @field:Attribute(name = "host")
    var host: String = ""
)

@Root(name = "servers", strict = false)
data class Servers(
    @field:ElementList(inline = true, required = false)
    var serverList: List<Server> = mutableListOf()
)

@Root(name = "settings", strict = false)
data class SpeedTestServers(
    @field:Element(name = "servers")
    var servers: Servers = Servers()
) {
    companion object {

        fun parseXml(xml: String): SpeedTestServers {
            return Persister().read(SpeedTestServers::class.java, xml)
        }
    }
}