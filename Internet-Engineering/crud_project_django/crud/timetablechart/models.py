from django.db import models
from django.urls import reverse


DAYS_OF_WEEK = (
    (0, 'شنبه'),
    (1, 'يکشنبه'),
    (2, 'دوشنبه'),
    (3, 'سه شنبه'),
    (4, 'چهارشنبه'),
    (5, 'پنج شنبه'),
)

class ChartPart(models.Model):
    start = models.TimeField(verbose_name='ساعت شروع کلاس')
    end = models.TimeField(verbose_name='ساعت پایان کلاس')
    day = models.IntegerField(choices=DAYS_OF_WEEK, verbose_name='روز')
    name = models.CharField(max_length=100, verbose_name='نام درس')
    professor = models.CharField(max_length=100, verbose_name='نام استاد یا ارائه دهنده')
    comments = models.CharField(max_length=200, verbose_name='توضیحات')
    
    def __str__(self):
        return DAYS_OF_WEEK[self.day][1] + ' | ' + str(self.start) + ' | ' + str(self.end) + ' | ' + self.comments + ' | ' + self.name + ' | ' + self.professor
    def day_str(self):
        return DAYS_OF_WEEK[self.day][1]
    def index_url(self):
        return reverse('timetablechart:index')
    def detail_url(self):
        return reverse('timetablechart:detail', kwargs={'id': self.id})
    def edit_url(self):
        return reverse('timetablechart:editpart', kwargs={'id': self.id})
    def rm_url(self):
        return reverse('timetablechart:rmpart', kwargs={'id': self.id})