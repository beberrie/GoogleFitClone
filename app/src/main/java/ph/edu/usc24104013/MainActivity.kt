package ph.edu.usc24104013

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ph.edu.usc24104013.databinding.ActivityMainBinding
import ph.edu.usc24104013.ui.home.LogActivityBottomSheet
import ph.edu.usc24104013.worker.WorkManagerScheduler

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isSpeedDialOpen = false

    // Pages where the FAB is visible
    private val fabVisibleDestinations = setOf(
        R.id.homeFragment,
        R.id.journalFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        requestPermissions()
        setupFab()
        WorkManagerScheduler.scheduleDailyReset(this)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        // Show/hide FAB based on current destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in fabVisibleDestinations) {
                binding.fabAdd.show()
            } else {
                binding.fabAdd.hide()
                if (isSpeedDialOpen) closeSpeedDial()
            }
        }
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            if (isSpeedDialOpen) closeSpeedDial() else openSpeedDial()
        }

        // Dim overlay closes the dial
        binding.fabDimOverlay.setOnClickListener { closeSpeedDial() }

        // Mini FAB: Log activity
        binding.fabLogActivity.setOnClickListener {
            closeSpeedDial()
            LogActivityBottomSheet().show(supportFragmentManager, "LogActivity")
        }

    }

    private fun openSpeedDial() {
        isSpeedDialOpen = true

        // Rotate + to X
        binding.fabAdd.animate().rotation(45f).setDuration(200).start()

        // Show dim overlay
        binding.fabDimOverlay.visibility = View.VISIBLE
        binding.fabDimOverlay.alpha = 0f
        binding.fabDimOverlay.animate().alpha(1f).setDuration(200).start()

        // Animate mini FABs in
        binding.fabActivityContainer.visibility = View.VISIBLE
        binding.fabWorkoutContainer.visibility  = View.VISIBLE

        val fadeSlideUp = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        binding.fabActivityContainer.startAnimation(fadeSlideUp)
        binding.fabWorkoutContainer.startAnimation(fadeSlideUp)
    }

    private fun closeSpeedDial() {
        isSpeedDialOpen = false

        // Rotate back
        binding.fabAdd.animate().rotation(0f).setDuration(200).start()

        // Hide dim overlay
        binding.fabDimOverlay.animate().alpha(0f).setDuration(200)
            .withEndAction { binding.fabDimOverlay.visibility = View.GONE }
            .start()

        // Animate mini FABs out
        val fadeSlideDown = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        binding.fabActivityContainer.startAnimation(fadeSlideDown)
        binding.fabWorkoutContainer.startAnimation(fadeSlideDown)

        fadeSlideDown.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(a: android.view.animation.Animation?) {}
            override fun onAnimationRepeat(a: android.view.animation.Animation?) {}
            override fun onAnimationEnd(a: android.view.animation.Animation?) {
                binding.fabActivityContainer.visibility = View.GONE
                binding.fabWorkoutContainer.visibility  = View.GONE
            }
        })
    }

    private fun requestPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}