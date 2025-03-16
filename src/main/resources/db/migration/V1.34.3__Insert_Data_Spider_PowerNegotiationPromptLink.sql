INSERT INTO PowerNegotiationPromptLink (PowerNegotiationPromptId, PowerNegotiationPromptResponseId, PersonalityId, NextPowerNegotiationPromptId, FollowOnSuccess, FollowOnFailure)
VALUES
-- The spider doesn't seem to understand...
    -- Say it again, but louder.
         (600, 0, 1, 601, 1, 0) -- The spider appears to be chuckling back at you...
            ,(601, null, -1, 625, 1, 1)
            ,(601, null, -1, 650, 1, 1)
        ,(600, 0, 2, 602, 1, 0) -- You got the spider's attention!
            ,(602, null, -1, 625, 1, 1)
            ,(602, null, -1, 650, 1, 1)
        ,(600, 0, 3, 603, 1, 0) -- The spider appears to be... bowing?
            ,(603, null, -1, 625, 1, 1)
            ,(603, null, -1, 650, 1, 1)
        ,(600, 0, 4, 604, 1, 0) -- The spider shows some signs of attention...
            ,(604, null, -1, 625, 1, 1)
            ,(604, null, -1, 650, 1, 1)

         (600, 0, 1, 605, 0, 1) -- The spider noisily leaps out!
        ,(600, 0, 2, 606, 0, 1) -- The spider's become defensive!
        ,(600, 0, 3, 607, 0, 1) -- The spider's loudly readying an attack!
        ,(600, 0, 4, 608, 0, 1) -- The spider's groaning with a deadly stare!
    -- Say it again, but slower.
         (600, 1, 1, 609, 1, 0) -- The spider's started moving in slow motion. It appears to be mocking you.
            ,(609, null, -1, 625, 1, 1)
            ,(609, null, -1, 650, 1, 1)
        ,(600, 1, 2, 610, 1, 0) -- The spider seems to be understanding a little better now?
            ,(610, null, -1, 625, 1, 1)
            ,(610, null, -1, 650, 1, 1)
        ,(600, 1, 3, 611, 1, 0) -- ...
            ,(611, null, -1, 625, 1, 1)
            ,(611, null, -1, 650, 1, 1)
        ,(600, 1, 4, 612, 1, 0) -- The spider seems a little more interested now.
            ,(612, null, -1, 625, 1, 1)
            ,(612, null, -1, 650, 1, 1)

         (600, 1, 1, 613, 0, 1) -- The spider excitedly leaps out at speed!
        ,(600, 1, 2, 614, 0, 1) -- The spider's become defensive!
        ,(600, 1, 3, 615, 0, 1) -- The spider looks to be readying an attack and definitely not slowly!
        ,(600, 1, 4, 616, 0, 1) -- It's no use. The spider isn't responding!
    -- Wait.
         (600, 2, 1, 617, 1, 0) -- The spider seems to have taken that as some sort of game.
            ,(617, null, -1, 625, 1, 1)
            ,(617, null, -1, 650, 1, 1)
        ,(600, 2, 2, 618, 1, 0) -- The spider remains silent...
            ,(618, null, -1, 625, 1, 1)
            ,(618, null, -1, 650, 1, 1)
        ,(600, 2, 3, 619, 1, 0) -- The spider seems to be waiting for you...
            ,(619, null, -1, 625, 1, 1)
            ,(619, null, -1, 650, 1, 1)
        ,(600, 2, 4, 620, 1, 0) -- The spider remains silent...
            ,(620, null, -1, 625, 1, 1)
            ,(620, null, -1, 650, 1, 1)

         (600, 2, 1, 621, 0, 1) -- The spider looks to have become bored. It's looking for other entertainment!
        ,(600, 2, 2, 622, 0, 1) -- The spider's become cynical of you!
        ,(600, 2, 3, 623, 0, 1) -- The spider's patience has worn thin! It's become aggressive!
        ,(600, 2, 4, 624, 0, 1) -- The spider's giving you an unnerving glare...
-- It seems to be eyeing up your inventory...
    -- Eye it back.
         (625, 3, 1, 626, 1, 0) -- The spider's starting a staring competition!
            ,(626, null, -1, 825, 1, 1)
        ,(625, 3, 2, 627, 1, 0) -- The spider is easing off...
            ,(627, null, -1, 826, 1, 1)
        ,(625, 3, 3, 628, 1, 0) -- ...Neither of you are breaking down.
            ,(628, null, -1, 827, 1, 1)
        ,(625, 3, 4, 629, 1, 0) -- The spider is gradually drifting its eyes away.
            ,(629, null, -1, 828, 1, 1)

         (625, 3, 1, 630, 0, 1) -- The spider's gaze has turned to you and become more deadly!
        ,(625, 3, 2, 631, 0, 1) -- The spider seems to now be considering something more nefarious!
        ,(625, 3, 3, 632, 0, 1) -- The spider's become agitated!
        ,(625, 3, 4, 633, 0, 1) -- The spider seems to have given up talking!
    -- Pretend to offer something.
         (625, 4, 1, 634, 1, 0) -- Seems the spider knows that's the oldest trick in the book! It's smirking!
            ,(634, null, -1, 825, 1, 1)
        ,(625, 4, 2, 635, 1, 0) -- Got 'em! The spider seems ashamed to have fallen for that.
            ,(635, null, -1, 826, 1, 1)
        ,(625, 4, 3, 636, 1, 0) -- ...
            ,(636, null, -1, 827, 1, 1)
        ,(625, 4, 4, 637, 1, 0) -- It didn't even bother looking up to notice...
            ,(637, null, -1, 828, 1, 1)

         (625, 4, 1, 638, 0, 1) -- Bad idea! The spider looks like it's about to play its own trick!
        ,(625, 4, 2, 639, 0, 1) -- The spider's shying away...
        ,(625, 4, 3, 730, 0, 1) -- Now it's irritated!
        ,(625, 4, 4, 731, 0, 1) -- It doesn't seem amused...
    -- Shake your head.
         (625, 5, 1, 642, 1, 0) -- The spider nods back.
            ,(642, null, -1, 825, 1, 1)
        ,(625, 5, 2, 643, 1, 0) -- It seems rather dismayed by your response...
            ,(643, null, -1, 826, 1, 1)
        ,(625, 5, 3, 644, 1, 0) -- The spider has retracted its attention.
            ,(644, null, -1, 827, 1, 1)
        ,(625, 5, 4, 645, 1, 0) -- It seems rather dismayed by your response...
            ,(645, null, -1, 828, 1, 1)

         (625, 5, 1, 646, 0, 1) -- The spider's shaking something up! It's readying to attack!
        ,(625, 5, 2, 647, 0, 1) -- It's lost interest in negotiation!
        ,(625, 5, 3, 648, 0, 1) -- That's riled it up!
        ,(625, 5, 4, 649, 0, 1) -- It's lost the will to continue on!
-- It appears to be deep in thought...
    -- Say it again.
         (650, 6, 1, 651, 1, 0) -- Oddly enough, it still doesn't seem to understand.
            ,(651, null, -1, 825, 1, 1)
        ,(650, 6, 2, 652, 1, 0) -- The spider appears concerned about your repetitive nature.
            ,(652, null, -1, 826, 1, 1)
        ,(650, 6, 3, 653, 1, 0) -- ...
            ,(653, null, -1, 827, 1, 1)
        ,(650, 6, 4, 654, 1, 0) -- The look it's giving you... The spider may be starting to take you for a parrot.
            ,(654, null, -1, 828, 1, 1)

         (650, 6, 1, 655, 0, 1) -- That's done it! It's had enough fun and games!
        ,(650, 6, 2, 656, 0, 1) -- The spider hasn't taken kindly to that!
        ,(650, 6, 3, 657, 0, 1) -- Once might've been enough! Now you're in trouble!
        ,(650, 6, 4, 658, 0, 1) -- It doesn't seem to have much hope for your intellect...
    -- Pull a pose like The Thinker.
         (650, 7, 1, 659, 1, 0) -- That seems to have humoured it. It seems friendlier now!
            ,(659, null, -1, 825, 1, 1)
        ,(650, 7, 2, 660, 1, 0) -- The spider seems to genuinely think you're in deep thought...
            ,(660, null, -1, 826, 1, 1)
        ,(650, 7, 3, 661, 1, 0) -- The spider gives you a nod.
            ,(661, null, -1, 827, 1, 1)
        ,(650, 7, 4, 662, 1, 0) -- It seems more at ease now.
            ,(662, null, -1, 828, 1, 1)

         (650, 7, 1, 663, 0, 1) -- There's no denying the face it's giving you - It thinks you look ridiculous!
        ,(650, 7, 2, 664, 0, 1) -- It hasn't taken well to being mocked!
        ,(650, 7, 3, 665, 0, 1) -- Looks like it doesn't have time for tomfoolery!
        ,(650, 7, 4, 666, 0, 1) -- That's set it off!
    -- Whistle a tune while waiting.
         (650, 8, 1, 667, 1, 0) -- It's getting its boogie on!
            ,(667, null, -1, 825, 1, 1)
        ,(650, 8, 2, 668, 1, 0) -- It seems like it wants to learn this tune...
            ,(668, null, -1, 826, 1, 1)
        ,(650, 8, 3, 669, 1, 0) -- Whistling a calming tune seems to have appeased it.
            ,(669, null, -1, 827, 1, 1)
        ,(650, 8, 4, 670, 1, 0) -- Whistling a gloomy tune, it seems to relate.
            ,(670, null, -1, 828, 1, 1)

         (650, 8, 1, 671, 0, 1) -- Looks like it's not a fan!
        ,(650, 8, 2, 672, 0, 1) -- It seems to prefer silence!
        ,(650, 8, 3, 673, 0, 1) -- It's lost its train of thought - Now it's mad!
        ,(650, 8, 4, 674, 0, 1) -- No bueno. That's irritated it!







-- The spider seems disinterested...
    -- Snap your fingers at it.
         (675, 9, 1, 676, 1, 0) -- It's got its eyes on you now!
            ,(676, null, -1, 700, 1, 1)
            ,(676, null, -1, 725, 1, 1)
        ,(675, 9, 2, 677, 1, 0) -- It's looking up with fear in its eyes.
            ,(677, null, -1, 700, 1, 1)
            ,(677, null, -1, 725, 1, 1)
        ,(675, 9, 3, 678, 1, 0) -- You've got its full attention with that!
            ,(678, null, -1, 700, 1, 1)
        ,(675, 9, 4, 679, 1, 0) -- It's giving you a look... But at least it's paying attention.
            ,(679, null, -1, 700, 1, 1)
            ,(679, null, -1, 725, 1, 1)

         (675, 9, 1, 680, 0, 1) -- It's snapping back!
        ,(675, 9, 2, 681, 0, 1) -- It hasn't responded well to hostility!
        ,(675, 9, 3, 682, 0, 1) -- That enraged it!
        ,(675, 9, 4, 683, 0, 1) -- It's turned on you!
    -- Tell it a fairy-tale.
         (675, 10, 1, 684, 1, 0) -- It seems to have enjoyed that.
            ,(684, null, -1, 700, 1, 1)
            ,(684, null, -1, 725, 1, 1)
        ,(675, 10, 2, 685, 1, 0) -- It looks like it's taken the message of the story to heart... Somehow.
            ,(685, null, -1, 700, 1, 1)
            ,(685, null, -1, 725, 1, 1)
        ,(675, 10, 3, 686, 1, 0) -- Its eyes are on you now. It might be waiting for another.
            ,(686, null, -1, 700, 1, 1)
            ,(686, null, -1, 725, 1, 1)
        ,(675, 10, 4, 687, 1, 0) -- It quietly listened. Seems you have its attention.
            ,(687, null, -1, 700, 1, 1)
            ,(687, null, -1, 725, 1, 1)

         (675, 10, 1, 688, 0, 1) -- Doesn't look like it's a fan of the classics!
        ,(675, 10, 2, 689, 0, 1) -- It hasn't taken the moral of the story to heart!
        ,(675, 10, 3, 690, 0, 1) -- No good. It's at a higher reading level.
        ,(675, 10, 4, 691, 0, 1) -- It doesn't appear to appreciate the happy ending.
    -- Stare into its deep beautiful eyes...
         (675, 11, 1, 692, 1, 0) -- As your eyes lock, you lean in for a kiss... Eurgh. No. It's a spider. Enough of that. At least that's garnered some attention...
            ,(692, null, -1, 700, 1, 1)
            ,(692, null, -1, 725, 1, 1)
        ,(675, 11, 2, 693, 1, 0) -- It's blushing...
            ,(693, null, -1, 700, 1, 1)
            ,(693, null, -1, 725, 1, 1)
        ,(675, 11, 3, 694, 1, 0) -- It's certainly more engaged now, and perhaps a little attracted...
            ,(694, null, -1, 700, 1, 1)
            ,(694, null, -1, 725, 1, 1)
        ,(675, 11, 4, 695, 1, 0) -- It's staring back, right into your very soul, with a longing for love in it's 8 sparkling peepers.
            ,(695, null, -1, 700, 1, 1)
            ,(695, null, -1, 725, 1, 1)

         (675, 11, 1, 696, 0, 1) -- The eyes are looking a little angrier now. Here it comes!
        ,(675, 11, 2, 697, 0, 1) -- That seems to have made it uncomfortable...
        ,(675, 11, 3, 698, 0, 1) -- It hasn't got time for that. Watch out!
        ,(675, 11, 4, 699, 0, 1) -- It doesn't appear to be in the mood...
-- The spider is beckoning you closer...
    -- Go closer.
         (700, 12, 1, 701, 1, 0) -- The spider seems elated! It's sniffing you up.
            ,(701, null, -1, 825, 1, 1)
        ,(700, 12, 2, 702, 1, 0) -- It's become more at ease.
            ,(702, null, -1, 826, 1, 1)
        ,(700, 12, 3, 703, 1, 0) -- Your prompt response seems to have appeased it!
            ,(703, null, -1, 827, 1, 1)
        ,(700, 12, 4, 704, 1, 0) -- Its demeanour seems to be slightly happier to have you close.
            ,(704, null, -1, 828, 1, 1)

         (700, 12, 1, 705, 0, 1) -- It was a trick! Watch out!
        ,(700, 12, 2, 706, 0, 1) -- Too close! It's retaliating!
        ,(700, 12, 3, 707, 0, 1) -- It was a revenge trick! Watch out!
        ,(700, 12, 4, 708, 0, 1) -- Looks like it wanted you to come closer for a fight!
    -- Stay back.
         (700, 13, 1, 709, 1, 0) -- It's slowly inching towards you instead!
            ,(709, null, -1, 825, 1, 1)
        ,(700, 13, 2, 710, 1, 0) -- It seems to relate to your shyness.
            ,(710, null, -1, 826, 1, 1)
        ,(700, 13, 3, 711, 1, 0) -- The spider seems to respect your determined stance!
            ,(711, null, -1, 827, 1, 1)
        ,(700, 13, 4, 712, 1, 0) -- Your decision has sent the spider into deep thought...
            ,(712, null, -1, 828, 1, 1)

         (700, 13, 1, 713, 0, 1) -- Not one for a stand-off it seems! Here it comes!
        ,(700, 13, 2, 714, 0, 1) -- It doesn't seem to trust you anymore!
        ,(700, 13, 3, 715, 0, 1) -- It's grown impatient! Watch out!
        ,(700, 13, 4, 716, 0, 1) -- It didn't appreciate your hesitation.
    -- Gesture back.
         (700, 14, 1, 717, 1, 0) -- It's coming over, with a skip in its step!
            ,(717, null, -1, 825, 1, 1)
        ,(700, 14, 2, 718, 1, 0) -- It's approaching cautiously...
            ,(718, null, -1, 826, 1, 1)
        ,(700, 14, 3, 719, 1, 0) -- It's coming over, though seems to have a bit of a huff on!
            ,(719, null, -1, 827, 1, 1)
        ,(700, 14, 4, 720, 1, 0) -- It appears curious as it slogs its way over.
            ,(720, null, -1, 828, 1, 1)

         (700, 14, 1, 721, 0, 1) -- It's charging over!!!
        ,(700, 14, 2, 722, 0, 1) -- It seems to suspect you're up to something. Watch out!
        ,(700, 14, 3, 723, 0, 1) -- It's not having any of that!
        ,(700, 14, 4, 724, 0, 1) -- It can't be bothered. It's grown more hostile.
-- It's looking at you expectantly...
    -- Show off your loot.
         (725, 15, 1, 726, 1, 0) -- The spider seems curious about your items.
            ,(726, null, -1, 825, 1, 1)
        ,(725, 15, 2, 727, 1, 0) -- It's inspecting your items with awe.
            ,(727, null, -1, 826, 1, 1)
        ,(725, 15, 3, 728, 1, 0) -- It appears to be quite impressed!
            ,(728, null, -1, 827, 1, 1)
        ,(725, 15, 4, 729, 1, 0) -- It seems perplexed by your treasures...
            ,(729, null, -1, 828, 1, 1)

         (725, 15, 1, 730, 0, 1) -- It doesn't look too impressed!
        ,(725, 15, 2, 731, 0, 1) -- It doesn't agree with your wealth!
        ,(725, 15, 3, 732, 0, 1) -- It hasn't taken well to your showing off!
        ,(725, 15, 4, 733, 0, 1) -- It's become rather bored...
    -- Bust a move.
         (725, 16, 1, 734, 1, 0) -- It's joined in a synchronised dance!
            ,(734, null, -1, 825, 1, 1)
        ,(725, 16, 2, 735, 1, 0) -- It appears rather impressed!
            ,(735, null, -1, 826, 1, 1)
        ,(725, 16, 3, 736, 1, 0) -- It seems to appreciate your skill.
            ,(736, null, -1, 827, 1, 1)
        ,(725, 16, 4, 737, 1, 0) -- It looks to be reading into your dance...
            ,(737, null, -1, 828, 1, 1)

         (725, 16, 1, 738, 0, 1) -- It was a ruse! It's laughing at you!
        ,(725, 16, 2, 739, 0, 1) -- It's intimidated by your raw skill. It knows it could never match up...
        ,(725, 16, 3, 740, 0, 1) -- It doesn't appear to be a fan of that favour of dance...
        ,(725, 16, 4, 741, 0, 1) -- It doesn't seem keen on the mainstream dance style...
    -- Enlighten it with knowledge.
         (725, 17, 1, 742, 1, 0) -- It appears flabbergasted by this new revelation!
            ,(742, null, -1, 825, 1, 1)
        ,(725, 17, 2, 743, 1, 0) -- It's carefully reflecting on this new knowledge...
            ,(743, null, -1, 826, 1, 1)
        ,(725, 17, 3, 744, 1, 0) -- It seems to appreciate your teachings.
            ,(744, null, -1, 827, 1, 1)
        ,(725, 17, 4, 745, 1, 0) -- It appears to be pondering over the meaning of life...
            ,(745, null, -1, 828, 1, 1)

         (725, 17, 1, 746, 0, 1) -- You've bored it!
        ,(725, 17, 2, 747, 0, 1) -- It feels stupid...
        ,(725, 17, 3, 748, 0, 1) -- It's offended by the simplicity of your teachings.
        ,(725, 17, 4, 749, 0, 1) -- It appears disinterested.




-- The spider is trying to get away...
    -- Stop it.
         (750, 18, 1, 751, 1, 0) -- You successfully held it back!
            ,(751, null, -1, 775, 1, 1)
            ,(751, null, -1, 800, 1, 1)
        ,(750, 18, 2, 752, 1, 0) -- You managed to keep it around!
            ,(752, null, -1, 800, 1, 1)
        ,(750, 18, 3, 753, 1, 0) -- The spider appears to respect your might and remains.
            ,(753, null, -1, 775, 1, 1)
            ,(753, null, -1, 800, 1, 1)
        ,(750, 18, 4, 754, 1, 0) -- The spider is curious of your intentions...
            ,(754, null, -1, 775, 1, 1)
            ,(754, null, -1, 800, 1, 1)

         (750, 18, 1, 755, 0, 1) -- No use! It's too fast!
        ,(750, 18, 2, 756, 0, 1) -- No use! It's scuttled away in fear!
        ,(750, 18, 3, 757, 0, 1) -- No use! It's determined to shoot off!
        ,(750, 18, 4, 758, 0, 1) -- No use! It's evading all attempts with reckless abandon!
    -- ...Let it leave.
         (750, 19, 1, 759, 1, 0) -- It's circled back around! It might've wanted to play chase.
            ,(759, null, -1, 775, 1, 1)
            ,(759, null, -1, 800, 1, 1)
        ,(750, 19, 2, 760, 1, 0) -- The spider stuck around after your kindness, it doesn't appear so intimidated anymore.
            ,(760, null, -1, 775, 1, 1)
            ,(760, null, -1, 800, 1, 1)
        ,(750, 19, 3, 761, 1, 0) -- ...
            ,(761, null, -1, 775, 1, 1)
            ,(761, null, -1, 800, 1, 1)
        ,(750, 19, 4, 762, 1, 0) -- It seems curious as to why you would let it get away...
            ,(762, null, -1, 775, 1, 1)
            ,(762, null, -1, 800, 1, 1)

         (750, 19, 1, 763, 0, 1) -- It's turned back with aggression!
        ,(750, 19, 2, 764, 0, 1) -- Gah! It was a trick to strike back!
        ,(750, 19, 3, 765, 0, 1) -- It was distancing for an attack!
        ,(750, 19, 4, 766, 0, 1) -- It's returning the favour, and not in a good way!
    -- Plead for it to stay.
         (750, 20, 1, 767, 1, 0) -- It seems humoured by your weakness and has stuck around.
            ,(767, null, -1, 775, 1, 1)
            ,(767, null, -1, 800, 1, 1)
        ,(750, 20, 2, 768, 1, 0) -- It's staying around to relieve you of your turmoil...
            ,(768, null, -1, 775, 1, 1)
            ,(768, null, -1, 800, 1, 1)
        ,(750, 20, 3, 769, 1, 0) -- It seems to appreciate your honesty and has stuck around.
            ,(769, null, -1, 775, 1, 1)
            ,(769, null, -1, 800, 1, 1)
        ,(750, 20, 4, 770, 1, 0) -- It's struck by your unusual behaviour!
            ,(770, null, -1, 775, 1, 1)
            ,(770, null, -1, 800, 1, 1)

         (750, 20, 1, 771, 0, 1) -- It's taken that as a sign of weakness!
        ,(750, 20, 2, 772, 0, 1) -- It's ineffective!
        ,(750, 20, 3, 773, 0, 1) -- It's annoyed by your blubbering. Watch out!
        ,(750, 20, 4, 774, 0, 1) -- It seems disinterested in your plea.
-- It looks like it might want to join you on your adventures...
    -- Gently let it down.
         (775, 21, 1, 776, 1, 0) -- It seems relatively unfazed!
            ,(776, null, -1, 825, 1, 1)
        ,(775, 21, 2, 777, 1, 0) -- It seems to have appreciated the soft response!
            ,(777, null, -1, 826, 1, 1)
        ,(775, 21, 3, 778, 1, 0) -- It seems to understand.
            ,(778, null, -1, 827, 1, 1)
        ,(775, 21, 4, 779, 1, 0) -- It appears disappointed, but understanding.
            ,(779, null, -1, 828, 1, 1)

         (775, 21, 1, 780, 0, 1) -- It hasn't taken that well!
        ,(775, 21, 2, 781, 0, 1) -- That's upset it!
        ,(775, 21, 3, 782, 0, 1) -- That's offended it!
        ,(775, 21, 4, 783, 0, 1) -- It's consumed with despair!
    -- Reject it.
         (775, 22, 1, 784, 1, 0) -- It's acting like it wasn't serious anyway! There's no way of telling if it was or not...
            ,(784, null, -1, 825, 1, 1)
        ,(775, 22, 2, 785, 1, 0) -- ...
            ,(785, null, -1, 826, 1, 1)
        ,(775, 22, 3, 786, 1, 0) -- It seems appeased by your direct nature!
            ,(786, null, -1, 827, 1, 1)
        ,(775, 22, 4, 787, 1, 0) -- Looks like it was anticipating that response...
            ,(787, null, -1, 828, 1, 1)

         (775, 22, 1, 788, 0, 1) -- It's ready to send you to the reject pile! Watch out!
        ,(775, 22, 2, 789, 0, 1) -- That's shaken it to the core!
        ,(775, 22, 3, 790, 0, 1) -- It doesn't appear to appreciate your tone.
        ,(775, 22, 4, 791, 0, 1) -- It's now longing for something more... deadly.
    -- Explain that you work alone.
         (775, 23, 1, 792, 1, 0) -- It's humoured by your attempt to look cool!
            ,(792, null, -1, 825, 1, 1)
        ,(775, 23, 2, 793, 1, 0) -- It seems to understand...
            ,(793, null, -1, 826, 1, 1)
        ,(775, 23, 3, 794, 1, 0) -- It's impressed!
            ,(794, null, -1, 827, 1, 1)
        ,(775, 23, 4, 795, 1, 0) -- It appears to relate to the sombre lifestyle...
            ,(795, null, -1, 828, 1, 1)

         (775, 23, 1, 796, 0, 1) -- It hasn't taken that well!
        ,(775, 23, 2, 797, 0, 1) -- That's upset it!
        ,(775, 23, 3, 798, 0, 1) -- That's offended it!
        ,(775, 23, 4, 799, 0, 1) -- It's consumed with despair!
-- It seems curious about who you are...
    -- Tell it that's secret.
         (800, 24, 1, 801, 1, 0) -- It seems pretty confident it can sniff you out.
            ,(801, null, -1, 825, 1, 1)
        ,(800, 24, 2, 802, 1, 0) -- It appears enamoured with your mysterious persona!
            ,(802, null, -1, 826, 1, 1)
        ,(800, 24, 3, 803, 1, 0) -- It seems a little antsy about not knowing.
            ,(803, null, -1, 827, 1, 1)
        ,(800, 24, 4, 804, 1, 0) -- It too seems to be harbouring secrets...
            ,(804, null, -1, 828, 1, 1)

         (800, 24, 1, 805, 0, 1) -- Looks like it's tired of playing "Guess Who?"!
        ,(800, 24, 2, 806, 0, 1) -- That seems to have destroyed any trust it had!
        ,(800, 24, 3, 807, 0, 1) -- Seems it's not keen on not knowing!
        ,(800, 24, 4, 808, 0, 1) -- It can sense your weak attempt to be honest edgy.
    -- Tell it you're a fancy banker.
         (800, 25, 1, 809, 1, 0) -- Looks like you may have just set yourself up as the Monopoly banker for Spider Games night next week.
            ,(809, null, -1, 825, 1, 1)
        ,(800, 25, 2, 810, 1, 0) -- It seems to appreciate your supposed honesty... Better not tell it too much more.
            ,(810, null, -1, 826, 1, 1)
        ,(800, 25, 3, 811, 1, 0) -- It... Looks like it wants financial advice?
            ,(811, null, -1, 827, 1, 1)
        ,(800, 25, 4, 812, 1, 0) -- It's dreaming about such a life for itself...
            ,(812, null, -1, 828, 1, 1)

         (800, 25, 1, 813, 0, 1) -- Nope. It's not buying that.
        ,(800, 25, 2, 814, 0, 1) -- It appears to be offended that you would think it so gullible.
        ,(800, 25, 3, 815, 0, 1) -- It's not taken well to your joking around!
        ,(800, 25, 4, 816, 0, 1) -- It seems a little too down on how its life turned out... Be careful!
    -- Tell it your life story.
         (800, 26, 1, 817, 1, 0) -- It seems to have really enjoyed the parts where you failed.
            ,(817, null, -1, 825, 1, 1)
        ,(800, 26, 2, 818, 1, 0) -- Understanding you better, it seems closer now.
            ,(818, null, -1, 826, 1, 1)
        ,(800, 26, 3, 819, 1, 0) -- It's listening intently - Seems honesty is the best policy!
            ,(819, null, -1, 827, 1, 1)
        ,(800, 26, 4, 820, 1, 0) -- It's enamoured by your story...
            ,(820, null, -1, 828, 1, 1)

         (800, 26, 1, 821, 0, 1) -- You've bored it into madness! Watch out!
        ,(800, 26, 2, 822, 0, 1) -- It's edging away, feeling I sufficient against your achievements.
        ,(800, 26, 3, 823, 0, 1) -- Your over-answering has really rather irritated it.
        ,(800, 26, 4, 824, 0, 1) -- It's become disinterested...
;