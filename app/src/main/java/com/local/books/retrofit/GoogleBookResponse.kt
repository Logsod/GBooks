package com.local.books.retrofit

data class GoogleBookResponse(
    val items: List<Item> = listOf(),
    val kind: String = "",
    val totalItems: Int = 0
) {
    data class Item(
        val accessInfo: AccessInfo = AccessInfo(),
        val etag: String = "",
        val id: String = "",
        val kind: String = "",
        val saleInfo: SaleInfo = SaleInfo(),
        val searchInfo: SearchInfo = SearchInfo(),
        val selfLink: String = "",
        val volumeInfo: VolumeInfo = VolumeInfo()
    ) {
        data class AccessInfo(
            val accessViewStatus: String = "",
            val country: String = "",
            val embeddable: Boolean = false,
            val epub: Epub = Epub(),
            val pdf: Pdf = Pdf(),
            val publicDomain: Boolean = false,
            val quoteSharingAllowed: Boolean = false,
            val textToSpeechPermission: String = "",
            val viewability: String = "",
            val webReaderLink: String = ""
        ) {
            data class Epub(
                val downloadLink: String = "",
                val isAvailable: Boolean = false
            )

            data class Pdf(
                val acsTokenLink: String = "",
                val downloadLink: String = "",
                val isAvailable: Boolean = false
            )
        }

        data class SaleInfo(
            val buyLink: String = "",
            val country: String = "",
            val isEbook: Boolean = false,
            val saleability: String = ""
        )

        data class SearchInfo(
            val textSnippet: String = ""
        )

        data class VolumeInfo(
            val allowAnonLogging: Boolean = false,
            val authors: List<String> = listOf(),
            val averageRating: Double = 0.0,
            val canonicalVolumeLink: String = "",
            val categories: List<String> = listOf(),
            val comicsContent: Boolean = false,
            val contentVersion: String = "",
            val description: String = "",
            val imageLinks: ImageLinks = ImageLinks(),
            val industryIdentifiers: List<IndustryIdentifier> = listOf(),
            val infoLink: String = "",
            val language: String = "",
            val maturityRating: String = "",
            val pageCount: Int = 0,
            val panelizationSummary: PanelizationSummary = PanelizationSummary(),
            val previewLink: String = "",
            val printType: String = "",
            val publishedDate: String = "",
            val publisher: String = "",
            val ratingsCount: Int = 0,
            val readingModes: ReadingModes = ReadingModes(),
            val subtitle: String = "",
            val title: String = ""
        ) {
            data class ImageLinks(
                val smallThumbnail: String = "",
                val thumbnail: String = ""
            )

            data class IndustryIdentifier(
                val identifier: String = "",
                val type: String = ""
            )

            data class PanelizationSummary(
                val containsEpubBubbles: Boolean = false,
                val containsImageBubbles: Boolean = false
            )

            data class ReadingModes(
                val image: Boolean = false,
                val text: Boolean = false
            )
        }
    }
}