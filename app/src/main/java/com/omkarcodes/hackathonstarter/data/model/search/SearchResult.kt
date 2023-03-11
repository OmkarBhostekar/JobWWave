package com.omkarcodes.hackathonstarter.data.model.search

data class SearchResult(
    val link: String = "",
    val position: Int,
    val rich_snippet: RichSnippet? = null,
    val snippet: String? = null,
    val snippet_highlighted_words: List<String>? = null,
    val title: String? = null
)