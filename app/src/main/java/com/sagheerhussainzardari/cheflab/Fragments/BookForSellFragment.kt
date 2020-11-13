package com.sagheerhussainzardari.cheflab.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.sagheerhussainzardari.cheflab.Models.BookSellModel
import com.sagheerhussainzardari.cheflab.R
import com.sagheerhussainzardari.cheflab.toastlong
import com.sagheerhussainzardari.cheflab.toastshort
import kotlinx.android.synthetic.main.fragment_sellbook.*


class BookForSellFragment : Fragment() {

    companion object {
        var firebaseStorage = FirebaseStorage.getInstance().reference
        var imageUri: Uri? = null
        var db = FirebaseDatabase.getInstance().reference
        var mAuth = FirebaseAuth.getInstance()

        var bookID = ""
        var bookName = ""
        var bookAuthor = ""
        var bookPrice = ""
        var bookCategory = ""
        var bookSellerUID = ""
        var bookSellerName = ""
        var bookSellerPhone = ""
        var bookSellerCity = ""
        var bookRatings = "0.0"
        var bookImageUrl = ""
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sellbook, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLayoutSellBook.setOnClickListener { }

        btn_ChoosePicture.setOnClickListener {
            pickImageFromGallary()
        }

        btn_SellBook.setOnClickListener {
            if (isInternetAvaiable())
                uploadSellBookDetails()
            else {
                context?.toastlong("Check Your Internet Connection!!!\nTry Again!!!")
            }
        }

    }

    private fun uploadSellBookDetails() {
        bookName = et_BookName_SellBook.text.toString()
        bookAuthor = et_BookAuthor_SellBook.text.toString()
        bookPrice = et_BookPrice_SellBook.text.toString()

        if (imageUri == null) {
            context?.toastshort("Please Select Book Image!!!")
            return
        }
        if (bookName.isEmpty()) {
            et_BookName_SellBook.error = "Field Must Not Be Empty!"
            et_BookName_SellBook.requestFocus()
            return
        }
        if (bookAuthor.isEmpty()) {
            et_BookAuthor_SellBook.error = "Field Must Not Be Empty!"
            et_BookAuthor_SellBook.requestFocus()
            return
        }
        if (bookPrice.isEmpty()) {
            et_BookPrice_SellBook.error = "Field Must Not Be Empty!"
            et_BookPrice_SellBook.requestFocus()
            return
        }

        if (bookPrice.toLowerCase().contains(Regex("[a-z]"))) {
            et_BookPrice_SellBook.error = "Only Digits Allowed Here.."
            et_BookPrice_SellBook.requestFocus()
            return
        }

        setBookDetailsInDatabaseForSell()
    }

    private fun setBookDetailsInDatabaseForSell() {
        getInfoForCurrentUser()
    }

    private fun getInfoForCurrentUser() {
        progressBarLayoutSellBook.visibility = View.VISIBLE

        db.child("Users").child(mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    bookSellerUID = mAuth.currentUser!!.uid
                    bookSellerName = p0.child("name").value.toString().trim()
                    bookSellerPhone = p0.child("phone").value.toString().trim()
                    bookSellerCity = p0.child("city").value.toString().trim()

                    getBookId()
                }
            })
    }

    private fun getBookId() {
        db.child("BooksForSell").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.value == null) {
                        bookID = "0"
                        uploadBookImage()
                    } else {
                        for (document in p0.children) {
                            bookID = "" + (document.key.toString().toInt() + 1)
                            uploadBookImage()
                        }
                    }
                }
            })
    }

    private fun uploadBookImage() {

        firebaseStorage.child(bookID).putFile(imageUri!!).addOnCompleteListener {
            if (it.isSuccessful) {
                it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                    bookImageUrl = downloadUrl.result.toString()
                    upadteBooksForSellListOnFirebaseDataBase()
                }
            } else {

                progressBarLayoutSellBook.visibility = View.GONE
                context?.toastshort("Failed")
            }
        }
    }

    private fun upadteBooksForSellListOnFirebaseDataBase() {
        db.child("BooksForSell").child(bookID).child("bookID").setValue(bookID)
        db.child("BooksForSell").child(bookID).child("bookName").setValue(bookName)
        db.child("BooksForSell").child(bookID).child("bookAuthor").setValue(bookAuthor)
        db.child("BooksForSell").child(bookID).child("bookPrice").setValue(bookPrice)
        db.child("BooksForSell").child(bookID).child("bookSellerUID").setValue(bookSellerUID)
        db.child("BooksForSell").child(bookID).child("bookSellerName").setValue(bookSellerName)
        db.child("BooksForSell").child(bookID).child("bookSellerPhone").setValue(bookSellerPhone)
        db.child("BooksForSell").child(bookID).child("bookSellerCity").setValue(bookSellerCity)
        db.child("BooksForSell").child(bookID).child("bookRatings").setValue(bookRatings)
        db.child("BooksForSell").child(bookID).child("bookImageUrl").setValue(bookImageUrl)


        updateBooksListInHome()
    }

    private fun updateBooksListInHome() {
        HomeFragment.db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                HomeFragment.booksList.clear()
                for (document in p0.children) {
                    HomeFragment.booksList.add(
                        BookSellModel(
                            document.child("bookID").value.toString(),
                            document.child("bookName").value.toString(),
                            document.child("bookAuthor").value.toString(),
                            document.child("bookPrice").value.toString(),
                            document.child("bookSellerUID").value.toString(),
                            document.child("bookSellerName").value.toString(),
                            document.child("bookSellerPhone").value.toString(),
                            document.child("bookSellerCity").value.toString(),
                            document.child("bookRatings").value.toString(),
                            document.child("bookImageUrl").value.toString()
                        )
                    )
                }

                progressBarLayoutSellBook.visibility = View.GONE
                context?.toastlong("Book Added Successfully")
            }
        })
    }


    private fun pickImageFromGallary() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            btn_ChoosePicture.error = null
            imageUri = data.data
            ivBookImage.setImageURI(imageUri)
            ivBookImage.visibility = View.VISIBLE

        } else {
            context?.toastshort("Please Select Book Image!!!")
        }

    }

    private fun isInternetAvaiable(): Boolean {
        val c = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return c.activeNetworkInfo != null && c.activeNetworkInfo.isConnected
    }

}