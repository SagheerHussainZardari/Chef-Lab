package com.sagheerhussainzardari.cheflab.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.sagheerhussainzardari.cheflab.Models.IngredentsModel
import com.sagheerhussainzardari.cheflab.R

class MyBooksForSellFragment : Fragment() {

    companion object {
        var booksList = ArrayList<IngredentsModel>()
        var mAuth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("BooksForSell")
        var firebaseStorage = FirebaseStorage.getInstance().reference
        var imageUri: Uri? = null

        var bookToEdit: IngredentsModel? = null

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mybooksforsell, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        booksList.clear()
//        getBooksList()

    }

//    private fun getBooksList() {
//        for (book in HomeFragment.booksList) {
//            if (book.bookSellerUID == mAuth.currentUser!!.uid)
//                booksList.add(
//                    IngredentsModel(
//                        book.bookId,
//                        book.bookName,
//                        book.bookAuthor,
//                        book.bookPrice,
//                        book.bookSellerUID,
//                        book.bookSellerName,
//                        book.bookSellerPhone,
//                        book.bookSellerCity,
//                        book.bookRatings,
//                        book.bookImageUrl
//                    )
//                )
//        }
//        setUpRecyclerView(booksList)
//    }

//    private fun setUpRecyclerView(booksList: ArrayList<IngredentsModel>) {
//        tv_listEmpty.setOnClickListener { }
//        if (booksList.isEmpty()) {
//            tv_listEmpty.visibility = View.VISIBLE
//        } else {
//            tv_listEmpty.visibility = View.GONE
//        }
//        recyclerview_booksellMyBooksForSell.setHasFixedSize(true)
//        recyclerview_booksellMyBooksForSell.layoutManager = GridLayoutManager(context, 1)
//        recyclerview_booksellMyBooksForSell.adapter =
//            BookSellMyBooksForSellAdapter(requireContext(), booksList, this)
//    }
//
//
//    fun onBookDelete(book: IngredentsModel) {
//
//
//        AlertDialog.Builder(requireContext()).setTitle("Warning...")
//            .setMessage("Do Your Want To Delete This Book?")
//            .setPositiveButton(
//                "Yes"
//            ) { dialog, which ->
//
//                progressbar_DeleteBook.visibility = View.VISIBLE
//                progressbar_DeleteBook.setOnClickListener { }
//
//                db.child(book.bookId).removeValue().addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        val imageRef =
//                            firebaseStorage.storage.getReferenceFromUrl(book.bookImageUrl)
//                        imageRef.delete().addOnCompleteListener {
//                            if (it.isSuccessful) {
//                                booksList.remove(book)
//                                setUpRecyclerView(booksList)
//                                updateBooksListInHome()
//
//                            } else {
//                                progressbar_DeleteBook.visibility = View.GONE
//                                context?.toastshort("Book Deletion Failed\nTry Again Later...")
//                            }
//                        }
//                    }
//                }
//
//            }.setNegativeButton(
//                "No"
//            ) { dialog, which ->
//
//            }.setCancelable(true).show()
//
//
//    }
//
//    private fun updateBooksListInHome() {
//        HomeFragment.db.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {}
//
//            override fun onDataChange(p0: DataSnapshot) {
//                HomeFragment.booksList.clear()
//                for (document in p0.children) {
//                    HomeFragment.booksList.add(
//                        IngredentsModel(
//                            document.child("bookID").value.toString(),
//                            document.child("bookName").value.toString(),
//                            document.child("bookAuthor").value.toString(),
//                            document.child("bookPrice").value.toString(),
//                            document.child("bookSellerUID").value.toString(),
//                            document.child("bookSellerName").value.toString(),
//                            document.child("bookSellerPhone").value.toString(),
//                            document.child("bookSellerCity").value.toString(),
//                            document.child("bookRatings").value.toString(),
//                            document.child("bookImageUrl").value.toString()
//                        )
//                    )
//                }
//
//                progressbar_DeleteBook.visibility = View.GONE
//                progressbar_EditBook.visibility = View.GONE
//
//            }
//        })
//    }
//
//    fun onBookEdit(book: IngredentsModel) {
//        bookToEdit = book
//        Glide.with(requireActivity()).load(book.bookImageUrl).into(ivBookImage_EditBook)
//        et_BookName_EditBook.setText(book.bookName)
//        et_BookAuthor_EditBook.setText(book.bookAuthor)
//        et_BookPrice_EditBook.setText(book.bookPrice)
//        et_BookPhone_EditBook.setText(book.bookSellerPhone)
//        et_BookCity_EditBook.setText(book.bookSellerCity)
//
//        bookEditLayout.setOnClickListener { }
//        bookEditLayout.visibility = View.VISIBLE
//
//        btn_ChoosePicture_EditBook.setOnClickListener {
//            pickImageFromGallary()
//        }
//
//        btn_EditBook.setOnClickListener {
//            uploadBookImage(book.bookId)
//        }
//    }
//
//    private fun pickImageFromGallary() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, 100)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
//            btn_ChoosePicture_EditBook.error = null
//            imageUri = data.data
//            ivBookImage_EditBook.setImageURI(imageUri)
//        } else {
//            context?.toastshort("Please Select Book Image!!!")
//        }
//
//    }
//
//
//    private fun uploadBookImage(bookId: String) {
//
//        progressbar_EditBook.visibility = View.VISIBLE
//        progressbar_EditBook.setOnClickListener { }
//
//        if (imageUri != null) {
//            BookForSellFragment.firebaseStorage.child(bookId).putFile(imageUri!!)
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
//                            val bookImageUrl = downloadUrl.result.toString()
//                            upadteBooksForSellListOnFirebaseDataBase(bookImageUrl)
//                        }
//                    } else {
//                        progressBarLayoutSellBook.visibility = View.GONE
//                        context?.toastshort("Failed")
//                    }
//                }
//
//        } else {
//            upadteBooksForSellListOnFirebaseDataBase(bookToEdit!!.bookImageUrl)
//        }
//    }
//
//    private fun upadteBooksForSellListOnFirebaseDataBase(bookImageUrl: String) {
//        val bookName = et_BookName_EditBook.text.toString().trim()
//        val bookAuthor = et_BookAuthor_EditBook.text.toString().trim()
//        val bookPhone = et_BookPhone_EditBook.text.toString().trim()
//        val bookPrice = et_BookPrice_EditBook.text.toString().trim()
//        val bookCity = et_BookCity_EditBook.text.toString().trim()
//
//        if (bookName.isEmpty()) {
//            et_BookName_EditBook.error = "Field Must Not Be Empty!!!"
//            et_BookName_EditBook.requestFocus()
//            return
//        }
//        if (bookAuthor.isEmpty()) {
//            et_BookAuthor_EditBook.error = "Field Must Not Be Empty!!!"
//            et_BookAuthor_EditBook.requestFocus()
//            return
//        }
//
//        if (bookPhone.isEmpty()) {
//            et_BookPhone_EditBook.error = "Field Must Not Be Empty!!!"
//            et_BookPhone_EditBook.requestFocus()
//            return
//        }
//        if (bookCity.isEmpty()) {
//            et_BookCity_EditBook.error = "Field Must Not Be Empty!!!"
//            et_BookCity_EditBook.requestFocus()
//            return
//        }
//
//        if (bookPhone.trim().length != 11 || !(Patterns.PHONE.matcher(bookPhone).matches())) {
//            et_BookPhone_EditBook.error = "Enter A Valid Phone Number!!\neg: 03063092274"
//            et_BookPhone_EditBook.requestFocus()
//            return
//        }
//        if (bookPrice.isEmpty()) {
//            et_BookPrice_EditBook.error = "Field Must Not Be Empty!!!"
//            et_BookPrice_EditBook.requestFocus()
//            return
//        }
//
//        if (bookPrice.toLowerCase().contains(Regex("[a-z]"))) {
//            et_BookPrice_EditBook.error = "Only Digits Allowed Here.."
//            et_BookPrice_EditBook.requestFocus()
//            return
//        }
//
//
//
//        db.child(bookToEdit!!.bookId).child("bookName").setValue(bookName)
//        db.child(bookToEdit!!.bookId).child("bookAuthor").setValue(bookAuthor)
//        db.child(bookToEdit!!.bookId).child("bookSellerPhone").setValue(bookPhone)
//        db.child(bookToEdit!!.bookId).child("bookSellerCity").setValue(bookCity)
//        db.child(bookToEdit!!.bookId).child("bookPrice").setValue(bookPrice)
//        db.child(bookToEdit!!.bookId).child("bookImageUrl").setValue(bookImageUrl)
//
//        updateBooksListInHome()
//        context?.toastshort("Book Updated Successfully...")
//    }


}