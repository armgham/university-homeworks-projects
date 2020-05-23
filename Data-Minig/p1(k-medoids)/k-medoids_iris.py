
import numpy as np
from numpy.random import choice

import pandas as pd
from sklearn import datasets

import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

iris = datasets.load_iris()
data = pd.DataFrame(iris.data,columns = iris.feature_names)

datapoints = data.values[:, 1:]  # just 3 feature for visualize (3d plot)


def random_medoids(data, k):
    samples = choice(len(data), size=k, replace=False)
    return data[samples, :]


def compute_d(X, medoids):
    m = len(X)
    medoids_shape = medoids.shape
    if len(medoids_shape) == 1: 
        medoids = medoids.reshape((1,len(medoids)))
    
    k = len(medoids)
    
    S = np.empty((m, k))
    
    for x in range(m):
        S[x] = [sum(i) for i in (X[x] - medoids) ** 2]
        
    return S


def update_medoids(X, medoids):
    
    S = compute_d(X, medoids)
    labels = np.argmin(S, axis=1)
        
    out_medoids = medoids
                
    for i in set(labels):
        
        avg_dissimilarity = np.sum(compute_d(X, medoids[i]))

        cluster_points = X[labels == i]
        
        for datap in cluster_points:
            new_medoid = datap
            new_dissimilarity= np.sum(compute_d(X, datap))
            
            if new_dissimilarity < avg_dissimilarity :
                avg_dissimilarity = new_dissimilarity
                
                out_medoids[i] = datap
                
    return out_medoids


def has_converged(old_medoids, medoids):
    return set([tuple(x) for x in old_medoids]) == set([tuple(x) for x in medoids])

def kmedoids(X, k, starting_medoids=None, max_steps=np.inf):
    if starting_medoids is None:
        medoids = random_medoids(X, k)
    else:
        medoids = starting_medoids
        
    converged = False
    labels = np.zeros(len(X))
    i = 1
    while (not converged) and (i <= max_steps):
        old_medoids = medoids.copy()
        
        S = compute_d(X, medoids)
        
        labels = np.argmin(S, axis=1)
        
        medoids = update_medoids(X, medoids)
        
        converged = has_converged(old_medoids, medoids)
        i += 1
    return (medoids,labels)



fig = plt.figure(figsize=plt.figaspect(0.5))

#First with k = 5
results = kmedoids(datapoints, 5)
labels = results[1]
ax = fig.add_subplot(2,2,1, projection='3d')
#ax = Axes3D(fig, elev=-150, azim=110)
points = datapoints
X_reduced = points
ax.scatter(X_reduced[:, 0], X_reduced[:, 1], X_reduced[:, 2], c=labels, cmap=plt.cm.Set1, edgecolor='k', s=40)
x=results[0][:, 0]
y=results[0][:, 1] 
z=results[0][:, 2] 
ax.scatter(x, y, z, color='black', marker='*', s=[20*4**2 for i in range(len(x))])
ax.title.set_text('k = 5')

#with k = 8
results = kmedoids(datapoints, 8)
labels = results[1]
ax = fig.add_subplot(2,2,2, projection='3d')
points = datapoints
X_reduced = points
ax.scatter(X_reduced[:, 0], X_reduced[:, 1], X_reduced[:, 2], c=labels, cmap=plt.cm.Set1, edgecolor='k', s=40)
x=results[0][:, 0]
y=results[0][:, 1] 
z=results[0][:, 2] 
ax.scatter(x, y, z, color='black', marker='*', s=[20*4**2 for i in range(len(x))])
ax.title.set_text('k = 8')


#with k = 10
results = kmedoids(datapoints, 10)
labels = results[1]
ax = fig.add_subplot(2,2,3, projection='3d')
points = datapoints
X_reduced = points
ax.scatter(X_reduced[:, 0], X_reduced[:, 1], X_reduced[:, 2], c=labels, cmap=plt.cm.Set1, edgecolor='k', s=40)
x=results[0][:, 0]
y=results[0][:, 1] 
z=results[0][:, 2] 
ax.scatter(x, y, z, color='black', marker='*', s=[20*4**2 for i in range(len(x))])
ax.title.set_text('k = 10')


#with k = 10
results = kmedoids(datapoints, 15)
labels = results[1]
ax = fig.add_subplot(2,2,4, projection='3d')
points = datapoints
X_reduced = points
ax.scatter(X_reduced[:, 0], X_reduced[:, 1], X_reduced[:, 2], c=labels, cmap=plt.cm.Set1, edgecolor='k', s=40)
x=results[0][:, 0]
y=results[0][:, 1] 
z=results[0][:, 2] 
ax.scatter(x, y, z, color='black', marker='*', s=[20*4**2 for i in range(len(x))])
ax.title.set_text('k = 15')


plt.show()



