package app.actionsfun.common.navigation.core

interface Destination {

    interface Screen : Destination

    interface Sheet : Destination

    interface Dialog : Destination

    interface Toast : Destination
}