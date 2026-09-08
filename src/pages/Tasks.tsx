import React, { useState, useMemo } from 'react';
import { Link } from 'react-router-dom';
import { format } from 'date-fns';
import { v4 as uuidv4 } from 'uuid';
import { Plus, X } from 'lucide-react';
import { store, Task } from '@/lib/store';
import { cn } from '@/lib/utils';

export default function Tasks() {
  const currentUser = store.getCurrentUser();
  const users = store.getUsers();
  
  const [tasks, setTasks] = useState<Task[]>(store.getTasks());
  const [isCreating, setIsCreating] = useState(false);
  
  const [newTask, setNewTask] = useState({
    title: '',
    description: '',
    dueDate: format(new Date(), 'yyyy-MM-dd'),
    assigneeId: '',
  });

  const visibleTasks = useMemo(() => {
    if (currentUser?.role === 'Admin') return tasks;
    return tasks.filter(t => t.assigneeId === currentUser?.id || t.creatorId === currentUser?.id);
  }, [tasks, currentUser]);

  const handleCreateTask = (e: React.FormEvent) => {
    e.preventDefault();
    if (!currentUser) return;

    const task: Task = {
      id: uuidv4(),
      title: newTask.title,
      description: newTask.description,
      status: 'Pending',
      assigneeId: newTask.assigneeId || undefined,
      creatorId: currentUser.id,
      dueDate: new Date(newTask.dueDate).toISOString(),
      createdAt: new Date().toISOString(),
    };

    const updatedTasks = [task, ...tasks];
    store.saveTasks(updatedTasks);
    setTasks(updatedTasks);
    setIsCreating(false);
    setNewTask({ title: '', description: '', dueDate: format(new Date(), 'yyyy-MM-dd'), assigneeId: '' });
  };

  return (
    <div className="space-y-6 max-w-5xl mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">Tasks</h1>
          <p className="mt-1 text-sm text-slate-500">Manage and track all tasks.</p>
        </div>
        {!isCreating && (
          <button
            onClick={() => setIsCreating(true)}
            className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 font-medium text-sm transition-colors"
          >
            <Plus className="w-4 h-4" />
            New Task
          </button>
        )}
      </div>

      {isCreating && (
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-lg font-semibold">Create New Task</h2>
            <button onClick={() => setIsCreating(false)} className="text-slate-400 hover:text-slate-600">
              <X className="w-5 h-5" />
            </button>
          </div>
          <form onSubmit={handleCreateTask} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Title</label>
              <input
                type="text"
                required
                value={newTask.title}
                onChange={e => setNewTask({...newTask, title: e.target.value})}
                className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Description</label>
              <textarea
                required
                rows={3}
                value={newTask.description}
                onChange={e => setNewTask({...newTask, description: e.target.value})}
                className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Assign To</label>
                <select
                  value={newTask.assigneeId}
                  onChange={e => setNewTask({...newTask, assigneeId: e.target.value})}
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-blue-500 focus:border-blue-500 bg-white"
                >
                  <option value="">Unassigned</option>
                  {users.map(u => (
                    <option key={u.id} value={u.id}>{u.name}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Due Date</label>
                <input
                  type="date"
                  required
                  value={newTask.dueDate}
                  onChange={e => setNewTask({...newTask, dueDate: e.target.value})}
                  className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:ring-blue-500 focus:border-blue-500"
                />
              </div>
            </div>
            <div className="flex justify-end pt-2">
              <button
                type="submit"
                className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 font-medium text-sm transition-colors"
              >
                Create Task
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
        <ul className="divide-y divide-slate-200">
          {visibleTasks.length === 0 ? (
            <li className="p-6 text-center text-slate-500 text-sm">No tasks found.</li>
          ) : (
            visibleTasks.map((task) => (
              <li key={task.id}>
                <Link to={`/tasks/${task.id}`} className="block hover:bg-slate-50 transition-colors p-6">
                  <div className="flex items-start justify-between">
                    <div className="pr-4">
                      <h3 className="text-base font-semibold text-slate-900">{task.title}</h3>
                      <p className="mt-1 text-sm text-slate-500 line-clamp-2">{task.description}</p>
                      <div className="mt-2 flex items-center gap-4 text-xs text-slate-400">
                        <span>Due: {format(new Date(task.dueDate), 'MMM d, yyyy')}</span>
                        {task.assigneeId && (
                          <span>
                            Assignee: {users.find(u => u.id === task.assigneeId)?.name || 'Unknown'}
                          </span>
                        )}
                      </div>
                    </div>
                    <div className="flex-shrink-0">
                      <span className={cn(
                        "inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium",
                        task.status === 'Completed' ? 'bg-emerald-100 text-emerald-800' : 
                        task.status === 'In Progress' ? 'bg-blue-100 text-blue-800' : 
                        'bg-slate-100 text-slate-800'
                      )}>
                        {task.status}
                      </span>
                    </div>
                  </div>
                </Link>
              </li>
            ))
          )}
        </ul>
      </div>
    </div>
  );
}
