package jp.studist.rinon2238.swipableimageviewersurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CardView>(R.id.transition_to_zoomage_horizontal).apply {
            setOnClickListener {
                startActivity(
                    ZoomageViewDemoActivity.createIntent(this@MainActivity, false)
                )
            }
        }

        findViewById<CardView>(R.id.transition_to_photoview_horizontal).apply {
            setOnClickListener {
                startActivity(
                    PhotoViewDemoActivity.createIntent(this@MainActivity, false)
                )
            }
        }

        findViewById<CardView>(R.id.transition_to_zoomage_vertical).apply {
            setOnClickListener {
                startActivity(
                    ZoomageViewDemoActivity.createIntent(this@MainActivity, true)
                )
            }
        }

        findViewById<CardView>(R.id.transition_to_photoview_vertical).apply {
            setOnClickListener {
                startActivity(
                    PhotoViewDemoActivity.createIntent(this@MainActivity, true)
                )
            }
        }

        findViewById<CardView>(R.id.transition_to_diy_ref_official).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, DIYReferenceOfficialActivity::class.java))
            }
        }
    }
}