from django.shortcuts import render, HttpResponse, get_object_or_404, HttpResponseRedirect
from .forms import PartForm
from .models import ChartPart


def index(request):
    parts = ChartPart.objects.all()
    return render(request, 'index.html', {'parts': parts})
    #return HttpResponse('<br>'.join([str(part) for part in parts] + ['<a href="./genchart">generate chart</a>']))


def gen_chart(request):
    from . import time_table_file
    parts = ChartPart.objects.all()
    datas = [str(part) for part in parts]
    svg = time_table_file.main(datas)
    return HttpResponse(svg, content_type='image/svg+xml')


def get_data_from_sada(request):
    if 'un' in request.POST and 'pw' in request.POST:
        username = request.POST['un']
        password = request.POST['pw']
        from . import scrap_requets
        from .models import DAYS_OF_WEEK
        datas = scrap_requets.main(username, password)
        for data in datas:
            cp = ChartPart(start=data[0], end=data[1], day=[d[1] for d in DAYS_OF_WEEK].index(data[2]), name=data[3], professor=data[4], comments=data[5])
            cp.save()
    return HttpResponseRedirect(cp.index_url())


def add_part(request):
    form = PartForm(request.POST or None)
    if form.is_valid():
        part = form.save()
        return HttpResponseRedirect(part.index_url())
    return render(request, 'add_part.html', context={'form': form})

def detail(request, id):
    part = get_object_or_404(ChartPart, id=id)
    part = str(part)
    from . import time_table_file
    parts = ChartPart.objects.all()
    datas = [str(prt) for prt in parts]
    svg = time_table_file.main(datas, part=part)
    return HttpResponse(svg, content_type='image/svg+xml')

def rm_part(request, id):
    part = get_object_or_404(ChartPart, id=id)
    part.delete()
    return HttpResponseRedirect(part.index_url())

def edit_part(request, id):
    part = get_object_or_404(ChartPart, id=id)
    form = PartForm(request.POST or None, instance=part)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect(part.index_url())
    
    return render(request, 'edit_part.html', {'form': form})