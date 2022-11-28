package com.github.polydome.usecase;

import com.github.polydome.data.MoodRepository;
import com.github.polydome.model.MoodEntry;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetScoresBreakdown {
    private final @NotNull MoodRepository moodRepository;

    public GetScoresBreakdown(@NotNull MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Retrieves aggregated score for each applicable day of a given month.
     * <p>
     * If no mood has been reported for a given day, the resulting map
     * does not contain such a day as a key.
     * </p>
     *
     * @param year  A valid year
     * @param month A valid month
     * @return A map of day index to score. Days are indexed from 0.
     * @throws IllegalArgumentException when either <code>year</code> or
     *                                  <code>month</code> is invalid.
     */
    public @NotNull Map<@NotNull Integer, @NotNull Integer> execute(int year, int month)
            throws IllegalArgumentException {
        Map<Integer, Integer> map = new HashMap<>();
        List<MoodEntry> entries = moodRepository.findEntriesReportedWithinPeriod(
                LocalDateTime.of(year, month, 1, 0, 0),
                LocalDateTime.of(year, month, getLastDayOfMonth(month), 23, 59));
        for (int day = 0; day < getLastDayOfMonth(month); day++) {
            final int fDay = day;
            List<MoodEntry> entriesForDay = entries.stream()
                    .filter((moodEntry) -> moodEntry.getDateTime().getDayOfMonth() == fDay).toList();
            int DailyScore = 0;
            int avgDailyScore = 0;
            for (MoodEntry entry : entriesForDay) {
                DailyScore += entry.getMood().getScore();
            }
            if (DailyScore != 0) {
                avgDailyScore = DailyScore / entriesForDay.size();
            }
            map.put(day, avgDailyScore);
        }
        return map;
    }

    private int getLastDayOfMonth(int month) {
        LocalDate currentdate = LocalDate.now();
        int currentMonth = currentdate.getMonthValue();
        int currentYear = currentdate.getYear();
        YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }
}
