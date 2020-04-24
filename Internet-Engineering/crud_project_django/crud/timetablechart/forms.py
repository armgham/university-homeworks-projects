from django import forms
from .models import ChartPart

class PartForm(forms.ModelForm):
    start = forms.TimeField(widget=forms.TimeInput(attrs={'placeholder': '08:00', 'class': 'validate'}, format='%H:%M'))
    end = forms.TimeField(widget=forms.TimeInput(attrs={'placeholder': '15:45', 'class': 'validate'}, format='%H:%M'))
    class Meta:
        model = ChartPart
        fields = ('__all__')
