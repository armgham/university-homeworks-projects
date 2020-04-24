from django.urls import path, re_path
from .views import add_part, rm_part, edit_part, index, gen_chart, get_data_from_sada, detail

app_name = 'timetablechart'

urlpatterns = [
    path('', index, name='index'),
    path('addpart/', add_part, name='addpart'),
    re_path(r'^(?P<id>\d+)/$', detail, name='detail'),
    re_path(r'^(?P<id>\d+)/rmpart/$', rm_part, name='rmpart'),
    re_path(r'^(?P<id>\d+)/editpart/$', edit_part, name='editpart'),
    path('genchart', gen_chart, name='genchart'),
    path('gdfsada', get_data_from_sada, name='gdfsada')
]