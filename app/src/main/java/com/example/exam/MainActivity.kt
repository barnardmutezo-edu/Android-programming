package com.example.exam
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private var results = ArrayList<Result>()
    private var database: DBManager = DBManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }










    fun switchFragment(v: View) {
        Toast.makeText(
            this,
            "Activity switch Fragment. Tag" + v.getTag().toString(),
            Toast.LENGTH_SHORT
        ).show()

        fragmentManager = supportFragmentManager
        val fragmentID = Integer.parseInt(v.getTag().toString());

        if (fragmentID == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.activity_main,
                    Fragment1(results),
                    "Fragment1"
                )
                .commit()
        } else if (fragmentID == 2) {

            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.activity_main,
                    Fragment2(results),
                    "Fragment2"
                )
                .commit()
        } else {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.activity_main,
                    Fragment3(database),
                    "Fragment3"
                )
                .commit()
        }
    }


}