# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from random import uniform
import matplotlib.pyplot as plt
from pprint import pprint


def find_min(d_m):
    d_m2 = [[d_m[i][j] if j<i else float('inf') for j in range(len(d_m[i]))] for i in range(len(d_m))]
    mins = [min(d) for d in d_m2]
    first_indexs = [d.index(min(d)) for d in d_m2]
    minimum = min(mins)
    second = mins.index(minimum)
    first = first_indexs[second]
    return first, second
    

def new_dm(points):
    d_m = []
    for p1s in points:
        d_m.append([])
        for p2s in points:
            if p2s['indexs'][0] <= p1s['indexs'][0]:
                d_m[-1].append(min([min([dist(p1, p2) for p2 in p2s['points']]) for p1 in p1s['points']]))
            else:
                d_m[-1].append(float('inf'))
    return d_m


def update_points(i, j, points):
    for js_index in points[j]['indexs']:
        points[i]['indexs'].append(js_index)
    for js_point in points[j]['points']:
        points[i]['points'].append(js_point)
    del ps[j]
    
    
def pretty_print_all(points, dist_mtr, n):
    if len(points) <= 2*n:
        n = len(points)
    print(' '*14, end="")
    
    
    group_str = lambda p: "(     %2i     )" %p['indexs'][0] if len(p['indexs'])==1\
    else "(   %2i,%2i    )" %(p['indexs'][0], p['indexs'][1]) if len(p['indexs'])==2\
    else "(  %2i,%2i,%2i  )" %(p['indexs'][0], p['indexs'][1], p['indexs'][2]) if len(p['indexs'])==3\
    else "(%2i,%2i,%2i,...)" %(p['indexs'][0], p['indexs'][1], p['indexs'][2])
    print('  '.join([group_str(p) for p in points[:n]]), end="")
    if len(points) > 2*n:
        print('  ...  ', end="")
        print('  '.join([group_str(p) for p in points[-1*n:]]))
    else:
        print()
    
    for p in points[:n]:
        print(group_str(p), end=" "*5)
        print(str('  '+' '*9).join("%5.2f" % f for f in dist_mtr[points.index(p)][:n]), end="")
        if len(points) > 2*n:
            print("      ...       ", end="")
            print(str('  '+' '*9).join("%5.2f" % f for f in dist_mtr[points.index(p)][-1*n:]))
        else:
            print()
    if len(points) > 2*n:
        print('..'+' '*12 + str('  '+' '*14)*n + '...')
        print('..'+' '*12 + str('  '+' '*14)*n + '...')
        print('..'+' '*12 + str('  '+' '*14)*n + '...')
        
        for p in points[-1*n:]:
            print(group_str(p), end=" "*5)
            print(str('  '+' '*9).join("%5.2f" % f for f in dist_mtr[points.index(p)][:n]), end="      ...       ")
            print(str('  '+' '*9).join("%5.2f" % f for f in dist_mtr[points.index(p)][-1*n:]))
    print('\nnumber of clusters: ', len(points))
    
    print('#'*14*2*n+'#'*14+'##'*n*2+'###' if len(points) > 2*n else '#'*14*n+'#'*14+'##'*n+'##')
    
def update_plt(plt, points, title=''):
    plt.cla()
    plt.clf()
    x = []
    y = []
    ls = []
    for pss in points:
        for p in pss['points']:
            x.append(p[0])
            y.append(p[1])
            ls.append(pss['indexs'][0])
    for i, txt in enumerate(ls):
        plt.annotate(txt, (x[i], y[i]))
        
    plt.scatter(x, y, c=ls, cmap=plt.cm.Set1, edgecolor='k')
    plt.suptitle(title)


ps =[{'indexs':[i], 'points':[(uniform(0., 15.), uniform(0., 15.))]} for i in range(100)]
dist = lambda p1, p2: sum([(p1[i] - p2[i]) ** 2 for i in range(len(p1))]) ** (1/2) #  Euclidean distance
dm = [[dist(p1['points'][0], p2['points'][0]) if p2['indexs'][0]<=p1['indexs'][0] else float('inf') for p2 in ps] for p1 in ps]

pretty_print_all(ps, dm, 4)
plt.figure(figsize=(10, 7))
update_plt(plt, ps, '10 iteration')
plt.pause(2)

itr = 1
while itr < 11:
    
    
    i, j = find_min(dm)
    update_points(i, j, ps)
    dm = new_dm(ps)
    
    pretty_print_all(ps, dm, 3)
    update_plt(plt, ps, '10 iteration : ' + str(itr))
    itr += 1
    plt.pause(1)
pprint(ps)

update_plt(plt, ps, 'ended! close it to start last question(5 clusters)')
plt.pause(0.1)
plt.show()



while len(ps) > 5:
    
    i, j = find_min(dm)
    update_points(i, j, ps)
    dm = new_dm(ps)
    
    pretty_print_all(ps, dm, 3)
    update_plt(plt, ps, '5 clusters  iter: ' + str(itr))
    itr += 1
    plt.pause(0.15)
    
pprint(ps)
plt.show()


# tedade iteration baraye residan be 5 cluster: 95
# chon har marhale yeki az tedade cluster ha kam mishavad.

