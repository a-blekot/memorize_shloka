package com.a_blekot.shlokas.android


//private fun moveToExternal() {
//    val filter = FileFilter { it.name.endsWith("shlokas") }
//    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        .listFiles()
//        ?.forEach {
//            Napier.d(it.name, tag = "DODODO")
////            val target = File(app.externalCacheDir, it.name)
////            it.copyTo(target, overwrite = true)
////            it.delete()
//        }
//}

////private val url = "https://drive.google.com/file/d/1r0luGFEBysTYX7eAwVB3VO_Yl4JOLXOO/view?usp=download" // Bhaktivaibhava
//private val url = "https://drive.google.com/file/d/1SBTWvNs1bEw6qhJ1emJ6EiujtNtHYnqx/view?usp=download" // anadi sb-1-3
//
////private val url = "https://fex.net/s/ydo5evs"
//private val mimeType = MimeTypes.MP3.value
//
//private fun downloadUsingIntent() {
//    val webpage: Uri = Uri.parse(url)
//    val intent = Intent(Intent.ACTION_VIEW, webpage)
//    if (intent.resolveActivity(app.packageManager) != null) {
//        app.currentActivity?.startActivity(intent)
//    }
//}
//
//private fun downloadFromGoogleDisk() {
//    GlobalScope.launch(Dispatchers.IO) {
//        val fileName = "AAAA_sb_1.1.1.mp3"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            saveFileUsingMediaStore(url, fileName, mimeType)
//        } else {
//            saveFileToExternalStorage(url, fileName)
//        }
//    }
//}
//
//private fun saveFileToExternalStorage(url: String, fileName: String) {
//    val target = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
//
//    URL(url).openStream().use { input ->
//        FileOutputStream(target).use { output ->
//            input.copyTo(output)
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.Q)
//private fun saveFileUsingMediaStore(url: String, fileName: String, mimeType: String) {
//    val contentValues = ContentValues().apply {
//        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
//        put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
//        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
//    }
//
//    val resolver = app.contentResolver
//
//    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//    if (uri != null) {
//        URL(url).openStream().use { input ->
//            resolver.openOutputStream(uri).use { output ->
////                input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
//
//                var bytesCopied: Long = 0
//                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//                var bytes = input.read(buffer)
//                while (bytes >= 0) {
//                    output!!.write(buffer, 0, bytes)
//                    bytesCopied += bytes
//                    Napier.w("bytesCopied = $bytesCopied", tag = "DODODO")
//                    bytes = input.read(buffer)
//                }
//            }
//        }
//    }
//}