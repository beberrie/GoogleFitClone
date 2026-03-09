package ph.edu.usc24104013.data.database

import ph.edu.usc24104013.data.entity.ExerciseEntity

object SeedData {
    val exercises = listOf(
        // ── Warm-ups ──────────────────────────────────────────────
        ExerciseEntity(name = "Jumping Jacks",         type = "Cardio",      phase = "warmup",   durationSeconds = 60,  description = "Jump feet wide while raising arms overhead.",              caloriesPerMinute = 8f),
        ExerciseEntity(name = "Arm Circles",           type = "Strength",    phase = "warmup",   durationSeconds = 45,  description = "Rotate arms in large circles forward and backward.",       caloriesPerMinute = 3f),
        ExerciseEntity(name = "Hip Rotations",         type = "Flexibility", phase = "warmup",   durationSeconds = 45,  description = "Stand and rotate hips in a wide circular motion.",        caloriesPerMinute = 3f),
        ExerciseEntity(name = "High Knees",            type = "Cardio",      phase = "warmup",   durationSeconds = 60,  description = "Run in place bringing knees to hip height.",              caloriesPerMinute = 9f),
        ExerciseEntity(name = "Leg Swings",            type = "Flexibility", phase = "warmup",   durationSeconds = 45,  description = "Hold a wall and swing each leg forward and back.",        caloriesPerMinute = 3f),
        ExerciseEntity(name = "Shoulder Rolls",        type = "Strength",    phase = "warmup",   durationSeconds = 30,  description = "Roll shoulders forward and backward in circles.",         caloriesPerMinute = 2f),
        ExerciseEntity(name = "Neck Tilts",            type = "Flexibility", phase = "warmup",   durationSeconds = 30,  description = "Slowly tilt head side to side and forward.",             caloriesPerMinute = 1f),
        ExerciseEntity(name = "Butt Kicks",            type = "Cardio",      phase = "warmup",   durationSeconds = 60,  description = "Jog in place kicking heels up to glutes.",               caloriesPerMinute = 7f),

        // ── Cardio Main ───────────────────────────────────────────
        ExerciseEntity(name = "Burpees",               type = "Cardio",      phase = "main",     durationSeconds = 40,  description = "Drop to plank, do a push-up, jump back up with arms raised.", caloriesPerMinute = 12f),
        ExerciseEntity(name = "Mountain Climbers",     type = "Cardio",      phase = "main",     durationSeconds = 40,  description = "In plank, alternate driving knees to chest rapidly.",     caloriesPerMinute = 11f),
        ExerciseEntity(name = "Jump Rope",             type = "Cardio",      phase = "main",     durationSeconds = 60,  description = "Jump continuously, simulating rope skipping motion.",     caloriesPerMinute = 12f),
        ExerciseEntity(name = "Box Jumps",             type = "Cardio",      phase = "main",     durationSeconds = 45,  description = "Jump onto an elevated surface and step back down.",      caloriesPerMinute = 10f),
        ExerciseEntity(name = "Sprint Intervals",      type = "Cardio",      phase = "main",     durationSeconds = 30,  description = "Sprint at full effort then rest, repeat.",               caloriesPerMinute = 14f),
        ExerciseEntity(name = "Jump Squats",           type = "Cardio",      phase = "main",     durationSeconds = 40,  description = "Squat down then explode upward into a jump.",            caloriesPerMinute = 10f),
        ExerciseEntity(name = "Lateral Shuffles",      type = "Cardio",      phase = "main",     durationSeconds = 45,  description = "Shuffle sideways quickly, touching the ground each end.", caloriesPerMinute = 9f),

        // ── Strength Main ─────────────────────────────────────────
        ExerciseEntity(name = "Push-ups",              type = "Strength",    phase = "main",     durationSeconds = 40,  description = "Lower chest to floor and push back up with arms.",       caloriesPerMinute = 7f),
        ExerciseEntity(name = "Squats",                type = "Strength",    phase = "main",     durationSeconds = 40,  description = "Feet shoulder-width apart, lower until thighs are parallel.", caloriesPerMinute = 6f),
        ExerciseEntity(name = "Lunges",                type = "Strength",    phase = "main",     durationSeconds = 40,  description = "Step forward and lower back knee toward ground.",        caloriesPerMinute = 6f),
        ExerciseEntity(name = "Plank",                 type = "Strength",    phase = "main",     durationSeconds = 45,  description = "Hold a straight body position on forearms and toes.",   caloriesPerMinute = 4f),
        ExerciseEntity(name = "Tricep Dips",           type = "Strength",    phase = "main",     durationSeconds = 40,  description = "Using a chair, lower and raise body by bending elbows.", caloriesPerMinute = 5f),
        ExerciseEntity(name = "Glute Bridges",         type = "Strength",    phase = "main",     durationSeconds = 40,  description = "Lie on back, push hips upward squeezing glutes.",        caloriesPerMinute = 5f),
        ExerciseEntity(name = "Superman Hold",         type = "Strength",    phase = "main",     durationSeconds = 35,  description = "Lie face down, raise arms and legs off the ground.",     caloriesPerMinute = 4f),
        ExerciseEntity(name = "Wall Sit",              type = "Strength",    phase = "main",     durationSeconds = 45,  description = "Lean against wall with thighs parallel to floor.",       caloriesPerMinute = 5f),

        // ── Flexibility Main ──────────────────────────────────────
        ExerciseEntity(name = "Yoga Flow",             type = "Flexibility", phase = "main",     durationSeconds = 60,  description = "Flow through child's pose, downward dog, and warrior.",  caloriesPerMinute = 3f),
        ExerciseEntity(name = "Pigeon Pose",           type = "Flexibility", phase = "main",     durationSeconds = 60,  description = "Hip opener: front leg bent, back leg extended behind.",  caloriesPerMinute = 2f),
        ExerciseEntity(name = "Seated Hamstring",      type = "Flexibility", phase = "main",     durationSeconds = 45,  description = "Sit and reach both hands toward extended foot.",         caloriesPerMinute = 2f),
        ExerciseEntity(name = "Spinal Twists",         type = "Flexibility", phase = "main",     durationSeconds = 45,  description = "Sit cross-legged and rotate torso side to side.",       caloriesPerMinute = 2f),
        ExerciseEntity(name = "Butterfly Stretch",     type = "Flexibility", phase = "main",     durationSeconds = 45,  description = "Sit with feet together, gently press knees to floor.",  caloriesPerMinute = 2f),
        ExerciseEntity(name = "Standing Side Bend",    type = "Flexibility", phase = "main",     durationSeconds = 40,  description = "Stand tall, reach one arm overhead and bend sideways.",  caloriesPerMinute = 2f),

        // ── Full Body Main ────────────────────────────────────────
        ExerciseEntity(name = "Deadlifts",             type = "Full Body",   phase = "main",     durationSeconds = 40,  description = "Hinge at hips to lift weight from floor to standing.",  caloriesPerMinute = 8f),
        ExerciseEntity(name = "Kettlebell Swings",     type = "Full Body",   phase = "main",     durationSeconds = 40,  description = "Swing kettlebell from hips to shoulder height.",        caloriesPerMinute = 10f),
        ExerciseEntity(name = "Thrusters",             type = "Full Body",   phase = "main",     durationSeconds = 40,  description = "Squat then press weights overhead in one motion.",      caloriesPerMinute = 10f),
        ExerciseEntity(name = "Renegade Rows",         type = "Full Body",   phase = "main",     durationSeconds = 40,  description = "In push-up position, row one dumbbell at a time.",      caloriesPerMinute = 8f),
        ExerciseEntity(name = "Bear Crawls",           type = "Full Body",   phase = "main",     durationSeconds = 40,  description = "On all fours, crawl forward keeping knees hovering.",   caloriesPerMinute = 9f),
        ExerciseEntity(name = "Turkish Get-Up",        type = "Full Body",   phase = "main",     durationSeconds = 50,  description = "From lying down, stand up while holding weight overhead.", caloriesPerMinute = 7f),

        // ── Cool-downs ────────────────────────────────────────────
        ExerciseEntity(name = "Child's Pose",          type = "Flexibility", phase = "cooldown", durationSeconds = 60,  description = "Kneel and extend arms forward along the floor.",        caloriesPerMinute = 1.5f),
        ExerciseEntity(name = "Standing Quad Stretch", type = "Flexibility", phase = "cooldown", durationSeconds = 45,  description = "Stand on one leg, pull other foot up to glutes.",       caloriesPerMinute = 1.5f),
        ExerciseEntity(name = "Shoulder Stretch",      type = "Flexibility", phase = "cooldown", durationSeconds = 45,  description = "Pull one arm across chest, hold with other arm.",       caloriesPerMinute = 1.5f),
        ExerciseEntity(name = "Deep Breathing",        type = "Flexibility", phase = "cooldown", durationSeconds = 60,  description = "Inhale 4 counts, hold 4, exhale 4 counts. Repeat.",    caloriesPerMinute = 1f),
        ExerciseEntity(name = "Cat-Cow Stretch",       type = "Flexibility", phase = "cooldown", durationSeconds = 60,  description = "On all fours, alternate arching and rounding spine.",   caloriesPerMinute = 1.5f),
        ExerciseEntity(name = "Seated Forward Fold",   type = "Flexibility", phase = "cooldown", durationSeconds = 45,  description = "Sit with legs out, hinge forward reaching for toes.",   caloriesPerMinute = 1.5f),
        ExerciseEntity(name = "Supine Twist",          type = "Flexibility", phase = "cooldown", durationSeconds = 45,  description = "Lie on back, drop knees to one side and hold.",         caloriesPerMinute = 1.5f)
    )
}