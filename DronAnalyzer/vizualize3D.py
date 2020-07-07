import numpy as np
import open3d as o3d
import os

cwd = os.getcwd()

print(cwd)
print("Load a ply point cloud, print it, and render it")
pcd = o3d.io.read_point_cloud(os.path.expanduser("~/Desktop") + "/dataPY.txt", format="xyz")
print(pcd)
print(np.asarray(pcd.points))
o3d.visualization.draw_geometries([pcd])
